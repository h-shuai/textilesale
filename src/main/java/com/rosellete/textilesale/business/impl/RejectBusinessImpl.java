package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RejectBusiness;
import com.rosellete.textilesale.model.*;
import com.rosellete.textilesale.service.*;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.ProductTypeVO;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("rejectBusiness")
public class RejectBusinessImpl implements RejectBusiness {
    @Autowired
    private RejectRecordService rejectRecordService;
    @Autowired
    private RejectSuppliesInfoService rejectSuppliesInfoService;
    @Autowired
    private RejectSuppliesStockDetailService rejectSuppliesStockDetailService;
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PackageInventoryInfoService packageInventoryInfoService;

    @Override
    public PageInfo<RejectRecordVO> getRejectRecordList(RejectRecordVO rejectRecordVO) {
        Page page = new Page(rejectRecordVO.getPageNum(), rejectRecordVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(rejectRecordVO);
        RejectRecord rejectRecord = new RejectRecord();
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(rejectRecordVO, rejectRecord, nullPropertyNames);
        BeanUtils.copyProperties(rejectRecordVO, supplierInfo, nullPropertyNames);
        List<RejectRecord> storageRecordList = rejectRecordService.findRecordList(rejectRecord, supplierInfo);
        List<RejectRecordVO> collect = storageRecordList.stream().map(e -> {
            RejectRecordVO temp = new RejectRecordVO();
            BeanUtils.copyProperties(e, temp);
            SupplierInfo info = supplierService.findByPrimaryKey(e.getSupplierNo());
            BeanUtils.copyProperties(info, temp);

            return temp;
        }).sorted(Comparator.comparing(RejectRecordVO::getRejectedDate).reversed()).collect(Collectors.toList());
        page.addAll(collect);
        page.setTotal(rejectRecordService.findRecordListSize(rejectRecord, supplierInfo));
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveRejectRecord(RejectRecordSaveVO rejectRecordSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(rejectRecordSaveVO);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(rejectRecordSaveVO, rejectRecord, nullPropertyNames);
        final List<ProductTypeVO> list = rejectRecordSaveVO.getProductTypeList().stream().
                filter(e -> StringUtils.isNotBlank(e.getProductType())&&!CollectionUtils.isEmpty(e.getPacketedStockArrays())).collect(Collectors.toList());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String creater = "admin";
        Date now = new Date();
        List<RejectRecord> toBeSavedRejectRecordList = new ArrayList<>();
        List<RejectSupplies> toBeInsertRejectSuppliesList = new ArrayList<>();
        List<RejectSuppliesStockDetail> toBeInsertStockDetailList = new ArrayList<>();
        if (StringUtils.isBlank(rejectRecord.getRecordNo()) && !CollectionUtils.isEmpty(list)) {
            String recordNo = new StringBuffer(LocalDateTime.now().format(dateTimeFormatter)).append(StringUtils.leftPad(String.valueOf(1), 6, "0")).toString();
            list.stream().forEach(e -> {
                final List<PackageInventory> stockList = e.getPacketedStockArrays().stream().
                        filter(stock -> null!=stock.getStockLength()&&0.0D!=stock.getStockLength()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(stockList)){
                    double productLength=0.0D;
                    for (int i = 0; i < stockList.size(); i++) {
                        final PackageInventory packageInventory = stockList.get(i);
                        RejectSuppliesStockDetail stockDetail = new RejectSuppliesStockDetail();
                        stockDetail.setRecordNo(recordNo);
                        stockDetail.setStockNo(i + 1);
                        stockDetail.setStockLength(packageInventory.getStockLength());
                        stockDetail.setProductType(e.getProductType());
                        stockDetail.setStatus("0");
                        stockDetail.setCreateUser(creater);
                        stockDetail.setUpdateUser(creater);
                        stockDetail.setCreateDate(now);
                        stockDetail.setUpdateDate(now);
                        toBeInsertStockDetailList.add(stockDetail);
                        productLength+=packageInventory.getStockLength();
                    }
                    RejectSupplies tmp = new RejectSupplies();
                    tmp.setRecordNo(recordNo);
                    tmp.setProductType(e.getProductType());
                    tmp.setImageName(packageInventoryInfoService.findLatestImageNameByProductType(tmp.getProductType()));
                    tmp.setProductLength(productLength);
                    tmp.setCreateUser(creater);
                    tmp.setUpdateUser(creater);
                    tmp.setCreateDate(now);
                    tmp.setUpdateDate(now);
                    toBeInsertRejectSuppliesList.add(tmp);
                }
            });
            RejectRecord tmp = new RejectRecord();
            tmp.setRecordNo(recordNo);
            long count = list.stream().map(e -> e.getProductType()).distinct().count();
            tmp.setProductTypeCount((int)count);
            tmp.setRejectedDate(now);
            tmp.setSupplierNo(rejectRecordSaveVO.getSupplierNo());
            tmp.setRejectSumLength(list.stream().map(e -> e.getPacketedStockArrays()).flatMap(e -> e.stream()).
                    filter(e->null!=e.getStockLength()&&0.0D!=e.getStockLength()).map(e -> e.getStockLength()).
                    reduce((a, b) -> a + b).get());
            tmp.setCreateUser(creater);
            tmp.setUpdateUser(creater);
            tmp.setCreateDate(now);
            tmp.setUpdateDate(now);
            toBeSavedRejectRecordList.add(tmp);
        } else if (!CollectionUtils.isEmpty(list)) {

            String recordNo = rejectRecord.getRecordNo();
            rejectRecord = rejectRecordService.findByPrimaryKey(recordNo);
            List<RejectSupplies> existedRejectSuppliesList = rejectSuppliesInfoService.findRecordDetailByRecordNo(recordNo);
            List<RejectSuppliesStockDetail> existedStockDetailList=rejectSuppliesStockDetailService.findAllByRecordNo(recordNo);

            list.stream().forEach(e -> {
                final List<PackageInventory> stockList = e.getPacketedStockArrays().stream().
                        filter(stock -> null!=stock.getStockLength()&&0.0D!=stock.getStockLength()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(stockList)){
                    for (int i = 0; i < stockList.size(); i++) {
                        final PackageInventory packageInventory = stockList.get(i);
                        RejectSuppliesStockDetail stockDetail = new RejectSuppliesStockDetail();
                        stockDetail.setStockNo(i + 1);
                        stockDetail.setRecordNo(recordNo);
                        stockDetail.setStockLength(packageInventory.getStockLength());
                        stockDetail.setProductType(e.getProductType());
                        stockDetail.setStatus("0");
                        stockDetail.setCreateUser(creater);
                        stockDetail.setUpdateUser(creater);
                        stockDetail.setCreateDate(now);
                        stockDetail.setUpdateDate(now);
                        toBeInsertStockDetailList.add(stockDetail);
                    }
                    RejectSupplies tmp = new RejectSupplies();
                    tmp.setRecordNo(recordNo);
                    tmp.setProductType(e.getProductType());
                    tmp.setImageName(packageInventoryInfoService.findLatestImageNameByProductType(tmp.getProductType()));
                    tmp.setProductLength(stockList.stream().map(s -> s.getStockLength()).reduce((a, b) -> a + b).get().doubleValue());
                    tmp.setCreateUser(creater);
                    tmp.setUpdateUser(creater);
                    tmp.setCreateDate(now);
                    tmp.setUpdateDate(now);
                    toBeInsertRejectSuppliesList.add(tmp);
                }

            });
            long count = list.stream().map(e -> e.getPacketedStockArrays()).flatMap(e -> e.stream()).
                    filter(e -> null != e.getStockLength() && 0.0D != e.getStockLength()).map(e -> e.getProductType()).distinct().count();
            rejectRecord.setProductTypeCount((int)count);
            rejectRecord.setRejectedDate(now);
            rejectRecord.setSupplierNo(rejectRecordSaveVO.getSupplierNo());
            rejectRecord.setRejectSumLength(list.stream().map(e -> e.getPacketedStockArrays()).flatMap(e -> e.stream()).
                    filter(e->null!=e.getStockLength()&&0.0D!=e.getStockLength()).map(e -> e.getStockLength()).
                    reduce((a, b) -> a + b).get());
            rejectRecord.setUpdateUser(creater);
            rejectRecord.setUpdateDate(now);
            toBeSavedRejectRecordList.add(rejectRecord);
            rejectSuppliesStockDetailService.deleteAll(existedStockDetailList);
            rejectSuppliesInfoService.deleteAll(existedRejectSuppliesList);
        }

        rejectSuppliesStockDetailService.saveAll(toBeInsertStockDetailList);
        rejectSuppliesInfoService.saveRejectSupplies(toBeInsertRejectSuppliesList);
        rejectRecordService.saveRejectRecord(toBeSavedRejectRecordList);
    }

    @Override
    public RejectRecordSaveVO getRejectRecordDetail(String recordNo) {
        RejectRecord record = rejectRecordService.findByPrimaryKey(recordNo);
        List<RejectSupplies> list = rejectSuppliesInfoService.findRecordDetailByRecordNo(recordNo);
        List<ProductTypeVO> collect = list.stream().map(e -> {
            ProductTypeVO temp = new ProductTypeVO();
            temp.setProductType(e.getProductType());
            final List<RejectSuppliesStockDetail> allByRecordNo = rejectSuppliesStockDetailService.findAllByRecordNoAndType(e.getRecordNo(),e.getProductType());
            final List<PackageInventory> inventoryList = allByRecordNo.stream().map(stock -> {
                PackageInventory inventory = new PackageInventory();
                inventory.setProductType(stock.getProductType());
                inventory.setStockLength(stock.getStockLength());
                return inventory;
            }).collect(Collectors.toList());
            temp.setPacketedStockArrays(inventoryList);
            temp.setProductLength(e.getProductLength());
            return temp;
        }).collect(Collectors.toList());
        RejectRecordSaveVO recordSaveVO=new RejectRecordSaveVO();
        BeanUtils.copyProperties(record,recordSaveVO);
        recordSaveVO.setProductTypeList(collect);
        return recordSaveVO;
    }
}
