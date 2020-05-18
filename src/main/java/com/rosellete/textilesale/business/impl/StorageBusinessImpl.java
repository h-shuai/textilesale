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
import java.time.LocalDateTime;
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
        List<StorageRecordVO> collect = storageRecordList.stream().map(e -> {
            StorageRecordVO temp = new StorageRecordVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void saveStorageRecord(StorageRecordVO storageRecordVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storageRecordVO);
        StorageRecord storageRecord = new StorageRecord();
        BeanUtils.copyProperties(storageRecordVO, storageRecord, nullPropertyNames);
        List<StoragePackageInfo> packageList = storageRecordVO.getPackageList().stream().filter(e -> null!=e.getPackedStockLength()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(packageList)) {
            Date now = new Date();
            String creator = "admin";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String recordNo =new StringBuffer(LocalDateTime.now().format(dateTimeFormatter)).append(StringUtils.leftPad(String.valueOf(1),6,"0")).toString();
            storageRecord.setCreateUser(creator);
            storageRecord.setCreateDate(now);
            StoragePackageInfo temp;
            int i;
            for (i = 0; i < packageList.size(); i++) {
                temp = packageList.get(i);
                temp.setRecordNo(recordNo);
                temp.setPackageNo(recordNo.concat("#").concat(StringUtils.leftPad(String.valueOf(1 + i), 3, '0')));
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
    public PageInfo<StoragePackageVO> getStoragePackage(String recordNo) {
        StorageRecord storageRecord = storageRecordService.findByPrimaryKey(recordNo);
        List<StoragePackageInfo> packageList = storagePackageInfoService.findAllByRecordNo(recordNo);
        List<StoragePackageVO> parsedList = packageList.stream().map(e -> {
            StoragePackageVO temp = new StoragePackageVO();
            BeanUtils.copyProperties(storageRecord, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public PageInfo<StoragePackageVO> getStoragePackageList(ConsignorVO consignorVO) {
        List<Map<String, Object>> resultSetList = storagePackageInfoService.findPackageList(consignorVO.getStorageType(),consignorVO.getConsignorNo());
        List<StoragePackageVO> collect = resultSetList.stream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, StoragePackageVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);

    }

    @Override
    public PageInfo<PackageInventoryInfoVO> getPackageInventory(String recordNo, String packageNo) {
        StorageRecord storageRecord = storageRecordService.findByPrimaryKey(recordNo);
        List<StoragePackageInfo> packageList = storagePackageInfoService.findStoragePackageByPackageNo(recordNo, packageNo);
        StoragePackageInfo storagePackageInfo = packageList.stream().findFirst().orElse(null);
        List<PackageInventoryInfo> inventoryInfoList = packageInventoryInfoService.findPackageInventoryByPackageNo(packageNo);
        List<PackageInventoryInfoVO> parsedList = inventoryInfoList.stream().map(e -> {
            PackageInventoryInfoVO temp = new PackageInventoryInfoVO();
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
        List<StoragePackageVO> packageList = packageInventorySaveVO.getPackageList().stream().
                filter(e -> null!=e.getPackedStockLength()&&0.0!=e.getPackedStockLength()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(packageList)){
            StoragePackageVO temp;
            StoragePackageInfo packageInfo;
            ProductTypeInfoVO productTypeInfoVO;
            String updator="admin";
            Date now=new Date();
            List<StoragePackageInfo> toBeUpdatePackageInfoList=new ArrayList<>(packageList.size());
            List<PackageInventoryInfo> toBeInsertedInventoryInfoList=new ArrayList<>();
            for (int i = 0; i <packageList.size() ; i++) {
                temp=packageList.get(i);
                packageInfo= storagePackageInfoService.findFirstStoragePackage(temp.getRecordNo(), temp.getPackageNo());
                List<ProductTypeInfoVO> productTypeList = temp.getProductTypeList().stream().
                        filter(e->null!=e.getProductLength()&&0.0!=e.getProductLength()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(productTypeList)){
                    for (int j = 0; j <productTypeList.size() ; j++) {
                        productTypeInfoVO = productTypeList.get(j);
                        List<PackageInventoryInfo> packetedStockArrays = productTypeInfoVO.getPacketedStockArrays().stream().
                                filter(e->null!=e.getStockLength()&&0.0!=e.getStockLength()).collect(Collectors.toList());
                        PackageInventoryInfo stock;
                        for (int k = 0; k <packetedStockArrays.size() ; k++) {
                            stock= packetedStockArrays.get(k);
                            stock.setPackageNo(temp.getPackageNo());
                            stock.setProductType(productTypeInfoVO.getProductType());
                            stock.setStockNo(k+1);
                            stock.setCreateDate(now);
                            stock.setCreateUser(updator);
                            stock.setUpdateDate(now);
                            stock.setUpdateUser(updator);
                            toBeInsertedInventoryInfoList.add(stock);
                        }
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
        List<ConsignorVO> collect = allSupplierAndCustomer.stream().map(e -> {
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
                storageType,consignorNo, consignor, consignorPhoneNo,consignorType, industryType);
        List<ConsignorVO> collect = allSupplierAndCustomer.stream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, ConsignorVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<ProductTypeInfoVO> findAllProductType() {
        List<String>  list=packageInventoryInfoService.findAllProductType();
        List<ProductTypeInfoVO> collect = list.stream().map(e -> {
            ProductTypeInfoVO temp = new ProductTypeInfoVO();
            temp.setProductType(e);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<PackageInventoryInfoVO> findStoredInventory(Integer supplierNo) {
        List<PackageInventoryInfo> list= packageInventoryInfoService.findStoredInventoryBySupplierNo(supplierNo);
        List<PackageInventoryInfoVO> collect = list.stream().map(e -> {
            PackageInventoryInfoVO temp = new PackageInventoryInfoVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }
}
