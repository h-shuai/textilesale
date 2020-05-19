package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RejectBusiness;
import com.rosellete.textilesale.model.PackageInventoryInfo;
import com.rosellete.textilesale.model.RejectRecord;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.service.RejectRecordService;
import com.rosellete.textilesale.service.RejectSuppliesInfoService;
import com.rosellete.textilesale.service.SupplierService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.ProductTypeInfoVO;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectRecordVO;
import com.rosellete.textilesale.vo.RejectSuppliesInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("rejectBusiness")
public class RejectBusinessImpl implements RejectBusiness {
    @Autowired
    private RejectRecordService rejectRecordService;
    @Autowired
    private RejectSuppliesInfoService rejectSuppliesInfoService;
    @Autowired
    private SupplierService supplierService;

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
            temp.setSupplierName(info.getName());
            temp.setIndustryType(info.getIndustry());
            temp.setSupplierPhoneNo(info.getPhone());
            temp.setSupplierType(info.getType());
            return temp;
        }).collect(Collectors.toList());
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
        final List<ProductTypeInfoVO> list = rejectRecordSaveVO.getProductTypeList().stream().
                filter(e -> !CollectionUtils.isEmpty(e.getPacketedStockArrays())).collect(Collectors.toList());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String creater = "admin";
        Date now = new Date();
        List<RejectRecord> toBeSavedRejectRecordList = new ArrayList<>();
        List<RejectSuppliesInfo> toBeInsertRejectSuppliesList = new ArrayList<>();
        if (StringUtils.isBlank(rejectRecord.getRecordNo())) {
            for (int i = 0; i < list.size(); i++) {
                String recordNo = new StringBuffer(LocalDateTime.now().format(dateTimeFormatter)).append(StringUtils.leftPad(String.valueOf(1+i), 6, "0")).toString();
                ProductTypeInfoVO productTypeInfo = list.get(i);
                List<PackageInventoryInfo> stockList = productTypeInfo.getPacketedStockArrays().stream().
                        filter(e -> null != e.getStockLength() && 0.0D != e.getStockLength()).collect(Collectors.toList());
                RejectRecord tmp = new RejectRecord();
                tmp.setRecordNo(recordNo);
                tmp.setProductCount(stockList.size());
                tmp.setRejectedDate(now);
                tmp.setSupplierNo(rejectRecordSaveVO.getSupplierNo());
                tmp.setProductType(productTypeInfo.getProductType());
                tmp.setCreateUser(creater);
                tmp.setUpdateUser(creater);
                tmp.setCreateDate(now);
                tmp.setUpdateDate(now);
                stockList.stream().forEach(e -> {
                    RejectSuppliesInfo info = new RejectSuppliesInfo();
                    info.setProductType(productTypeInfo.getProductType());
                    info.setRecordNo(recordNo);
                    info.setProductLength(e.getStockLength());
                    info.setCreateUser(creater);
                    info.setUpdateUser(creater);
                    info.setCreateDate(now);
                    info.setUpdateDate(now);
                    toBeInsertRejectSuppliesList.add(info);
                });
                toBeSavedRejectRecordList.add(tmp);
            }
        } else {
            String recordNo = rejectRecord.getRecordNo();
            rejectRecord = rejectRecordService.findByPrimaryKey(recordNo);
            Stream<String> distinct = list.stream().map(e -> e.getProductType()).distinct();
            long count = distinct.count();
            if (count > 1) {
                throw new IllegalArgumentException("修改时不能新增产品型号!");
            }
            String type = distinct.findAny().orElse(null);
            if (StringUtils.equals(rejectRecord.getProductType(), type)) {
                throw new IllegalArgumentException("不能修改产品型号!");
            }
            List<PackageInventoryInfo> stockList = list.stream().filter(e -> StringUtils.isNoneBlank(e.getProductType())).findFirst().orElse(new ProductTypeInfoVO()).
                    getPacketedStockArrays().stream().filter(e -> null != e.getStockLength() && 0.0D != e.getStockLength()).collect(Collectors.toList());
            stockList.forEach(e -> {
                RejectSuppliesInfo info = new RejectSuppliesInfo();
                info.setProductType(e.getProductType());
                info.setRecordNo(recordNo);
                info.setProductLength(e.getStockLength());
                info.setCreateUser(creater);
                info.setUpdateUser(creater);
                info.setCreateDate(now);
                info.setUpdateDate(now);
                toBeInsertRejectSuppliesList.add(info);
            });
            rejectRecord.setProductCount(stockList.size());
            rejectRecord.setUpdateUser(creater);
            rejectRecord.setUpdateDate(now);
            toBeSavedRejectRecordList.add(rejectRecord);
        }

        rejectSuppliesInfoService.saveRejectSupplies(toBeInsertRejectSuppliesList);
        rejectRecordService.saveRejectRecord(toBeSavedRejectRecordList);
    }

    @Override
    public PageInfo<RejectSuppliesInfoVO> getRejectRecordDetail(String recordNo) {
        RejectRecord record = rejectRecordService.findByPrimaryKey(recordNo);
        List<RejectSuppliesInfo> list = rejectSuppliesInfoService.findRecordDetailByRecordNo(recordNo);
        List<RejectSuppliesInfoVO> collect = list.stream().map(e -> {
            RejectSuppliesInfoVO temp = new RejectSuppliesInfoVO();
            BeanUtils.copyProperties(record, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }
}
