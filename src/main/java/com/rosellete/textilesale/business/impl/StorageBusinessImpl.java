package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.StorageBusiness;
import com.rosellete.textilesale.model.*;
import com.rosellete.textilesale.service.PackageInventoryInfoService;
import com.rosellete.textilesale.service.StoragePackageInfoService;
import com.rosellete.textilesale.service.StorageRecordService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("storageBusiness")
public class StorageBusinessImpl implements StorageBusiness {

    @Autowired
    private StorageRecordService storageRecordService;

    @Autowired
    private StoragePackageInfoService storagePackageInfoService;

    @Autowired
    private PackageInventoryInfoService packageInventoryInfoService;

    @Override
    public PageInfo<StorageRecordVO> getStorageRecordList(StorageRecordVO storageRecordVO) {
        Page page = new Page(storageRecordVO.getPageNum(), storageRecordVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storageRecordVO);
        StorageRecord storageRecord = new StorageRecord();
        CustomerInfo customerInfo = new CustomerInfo();
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(storageRecordVO, storageRecord, nullPropertyNames);
        BeanUtils.copyProperties(storageRecordVO, customerInfo, nullPropertyNames);
        BeanUtils.copyProperties(storageRecordVO, supplierInfo, nullPropertyNames);
        List<StorageRecord> storageRecordList;
        Date startDate = storageRecordVO.getStartDate(), endDate = storageRecordVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            storageRecordList = storageRecordService.getStorageRecordList(storageRecord, supplierInfo, customerInfo, startDate, endDate);
            page.setTotal(storageRecordService.getStorageRecordListSize(storageRecord, supplierInfo, customerInfo, startDate, endDate));
        } else {
            storageRecordList = storageRecordService.getStorageRecordList(storageRecord, supplierInfo, customerInfo, null, null);
            page.setTotal(storageRecordService.getStorageRecordListSize(storageRecord, supplierInfo, customerInfo, null, null));
        }
        List<StorageRecordVO> collect = storageRecordList.parallelStream().map(e -> {
            StorageRecordVO temp = new StorageRecordVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).sorted(Comparator.comparing(StorageRecordVO::getStartDate).reversed()).collect(Collectors.toList());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void saveStorageRecord(StorageRecordVO storageRecordVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storageRecordVO);
        StorageRecord storageRecord = new StorageRecord();
        BeanUtils.copyProperties(storageRecordVO, storageRecord, nullPropertyNames);
        List<StoragePackage> packageList = storageRecordVO.getPackageList().parallelStream().filter(e -> null != e.getPackedStockLength()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(packageList)) {
            Date now = new Date();
            String creator = "admin";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            Integer recordNo = this.getSequenceNo();
            storageRecord.setCreateUser(creator);
            storageRecord.setCreateDate(now);
            StoragePackage temp;
            int i;
            for (i = 0; i < packageList.size(); i++) {
                temp = packageList.get(i);
                temp.setRecordNo(recordNo);
                temp.setPackageNo(new StringBuffer().append(recordNo).append("#").append(StringUtils.leftPad(String.valueOf(1 + i), 3, '0')).toString());
                temp.setPackageStatus("0");
                temp.setCreateUser(creator);
                temp.setCreateDate(now);
                temp.setUpdateUser(creator);
                temp.setUpdateDate(now);
            }
            storageRecord.setRecordNo(recordNo);
            storageRecord.setStorageDate(now);
            storageRecord.setPackageQuantity(i);
            storageRecord.setUpdateUser(creator);
            storageRecord.setUpdateDate(now);
            storagePackageInfoService.saveStoragePackageList(packageList);
            storageRecordService.saveStorageRecord(storageRecord);
        }

    }

    @Override
    public PageInfo<StoragePackageVO> getStoragePackage(Integer recordNo) {
        StorageRecord storageRecord = storageRecordService.findByPrimaryKey(recordNo);
        List<StoragePackage> packageList = storagePackageInfoService.findAllByRecordNo(recordNo);
        List<StoragePackageVO> parsedList = packageList.parallelStream().map(e -> {
            StoragePackageVO temp = new StoragePackageVO();
            BeanUtils.copyProperties(storageRecord, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public PageInfo<StoragePackageVO> getStoragePackageList(ConsignorVO consignorVO) {
        List<Map<String, Object>> resultSetList = storagePackageInfoService.findPackageList(consignorVO.getStorageType(), consignorVO.getConsignorNo());
        List<StoragePackageVO> collect = resultSetList.parallelStream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, StoragePackageVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);

    }

    @Override
    public PageInfo<PackageInventoryVO> getPackageInventory(Integer recordNo, String packageNo) {
        StorageRecord storageRecord = storageRecordService.findByPrimaryKey(recordNo);
        List<StoragePackage> packageList = storagePackageInfoService.findStoragePackageByPackageNo(recordNo, packageNo);
        StoragePackage storagePackageInfo = packageList.parallelStream().findFirst().orElse(null);
        List<PackageInventory> inventoryInfoList = packageInventoryInfoService.findPackageInventoryByPackageNo(packageNo);
        List<PackageInventoryVO> parsedList = inventoryInfoList.parallelStream().map(e -> {
            PackageInventoryVO temp = new PackageInventoryVO();
            BeanUtils.copyProperties(storageRecord, temp);
            BeanUtils.copyProperties(storagePackageInfo, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void savePackageInventoryList(PackageInventorySaveVO packageInventorySaveVO) {
        List<StoragePackageVO> packageList = packageInventorySaveVO.getPackageList().parallelStream().
                filter(e -> null != e.getPackedStockLength() && 0.0 != e.getPackedStockLength()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(packageList)) {
            StoragePackageVO temp;
            StoragePackage packageInfo;
            ProductTypeVO productTypeInfoVO;
            String updator = "admin";
            Date now = new Date();
            List<StoragePackage> toBeUpdatePackageInfoList = new ArrayList<>(packageList.size());
            List<PackageInventory> toBeInsertedInventoryInfoList = new ArrayList<>();
            for (int i = 0; i < packageList.size(); i++) {
                temp = packageList.get(i);
                packageInfo = storagePackageInfoService.findFirstStoragePackage(temp.getRecordNo(), temp.getPackageNo());
                List<ProductTypeVO> productTypeList = temp.getProductTypeList().parallelStream().
                        filter(e -> null != e.getProductLength() && 0.0 != e.getProductLength()).collect(Collectors.toList());
                double stockLength = 0.0;
                if (!CollectionUtils.isEmpty(productTypeList)) {
                    for (int j = 0; j < productTypeList.size(); j++) {
                        productTypeInfoVO = productTypeList.get(j);
                        List<PackageInventory> packetedStockArrays = productTypeInfoVO.getPacketedStockArrays().parallelStream().
                                filter(e -> null != e.getStockLength() && 0.0 != e.getStockLength()).collect(Collectors.toList());
                        PackageInventory stock;
                        for (int k = 0; k < packetedStockArrays.size(); k++) {
                            stock = packetedStockArrays.get(k);
                            stock.setPackageNo(temp.getPackageNo());
                            stock.setProductType(productTypeInfoVO.getProductType());
                            stock.setImageName(productTypeInfoVO.getImageName());
                            stock.setStockNo(k + 1);
                            stock.setCreateDate(now);
                            stock.setCreateUser(updator);
                            stock.setUpdateDate(now);
                            stock.setUpdateUser(updator);
                            stockLength += stock.getStockLength();
                            toBeInsertedInventoryInfoList.add(stock);
                        }
                    }
                    if (stockLength != packageInfo.getPackedStockLength()) {
                        throw new IllegalArgumentException("包裹物品总长度与拆包明细合计长度不等，请检查");
                    }
                    packageInfo.setPackageStatus("1");
                    packageInfo.setUpdateUser(updator);
                    packageInfo.setUpdateDate(now);
                    toBeUpdatePackageInfoList.add(packageInfo);
                }

            }
            storagePackageInfoService.saveStoragePackageList(toBeUpdatePackageInfoList);
            packageInventoryInfoService.savePackageInventoryList(toBeInsertedInventoryInfoList);
        }
    }

    @Override
    public PageInfo<ConsignorVO> findAllSupplierAndCustomerWarehouseRelated() {
        List<Map<String, String>> allSupplierAndCustomer = storageRecordService.findAllSupplierAndCustomer();
        List<ConsignorVO> collect = allSupplierAndCustomer.parallelStream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, ConsignorVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<ConsignorVO> findSupplierAndCustomerWarehouseRelated(ConsignorVO consignorVO) {
        String storageType = StringUtils.trimToNull(consignorVO.getStorageType());
        Integer consignorNo = consignorVO.getConsignorNo();
        String consignor = StringUtils.trimToNull(consignorVO.getConsignor());
        String consignorPhoneNo = StringUtils.trimToNull(consignorVO.getConsignorPhoneNo());
        String consignorType = StringUtils.trimToNull(consignorVO.getConsignorType());
        String industryType = StringUtils.trimToNull(consignorVO.getIndustryType());
        List<Map<String, String>> allSupplierAndCustomer = storageRecordService.findSupplierAndCustomer(
                storageType, consignorNo, consignor, consignorPhoneNo, consignorType, industryType,
                consignorVO.getPageNum(), consignorVO.getPageSize());
        List<ConsignorVO> collect = allSupplierAndCustomer.parallelStream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, ConsignorVO.class);
        }).collect(Collectors.toList());
        com.github.pagehelper.Page page = new com.github.pagehelper.Page(consignorVO.getPageNum(), consignorVO.getPageSize());
        page.setTotal(storageRecordService.getSupplierAndCustomerListSize(
                storageType, consignorNo, consignor, consignorPhoneNo, consignorType, industryType));
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Override
    public PageInfo<ProductTypeVO> findAllProductType() {
        List<String> list = packageInventoryInfoService.findAllProductType();
        List<ProductTypeVO> collect = list.parallelStream().map(e -> {
            ProductTypeVO temp = new ProductTypeVO();
            temp.setProductType(e);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<ProductTypeVO> findStoredInventory(Integer supplierNo) {
        List<PackageInventory> list = packageInventoryInfoService.findStoredInventoryBySupplierNo(supplierNo);
        Map<String, List<PackageInventory>> map = list.parallelStream().collect(Collectors.groupingBy(PackageInventory::getProductType));
        Iterator<Map.Entry<String, List<PackageInventory>>> iterator = map.entrySet().iterator();
        List<ProductTypeVO> productTypeList = new ArrayList<>(10);
        while (iterator.hasNext()) {
            Map.Entry<String, List<PackageInventory>> next = iterator.next();
            ProductTypeVO temp = new ProductTypeVO();
            temp.setProductType(next.getKey());
            temp.setPacketedStockArrays(next.getValue());
            productTypeList.add(temp);
        }
        return new PageInfo<>(productTypeList);
    }

    @Override
    public Integer getSequenceNo() {
        return storageRecordService.getMaxRecordNo() + 1;
    }
}
