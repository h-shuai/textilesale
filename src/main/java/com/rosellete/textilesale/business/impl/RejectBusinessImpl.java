package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RejectBusiness;
import com.rosellete.textilesale.model.RejectRecord;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.service.RejectRecordService;
import com.rosellete.textilesale.service.RejectSuppliesInfoService;
import com.rosellete.textilesale.service.SupplierService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectRecordVO;
import com.rosellete.textilesale.vo.RejectSuppliesInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        SupplierInfo supplierInfo =new SupplierInfo();
        BeanUtils.copyProperties(rejectRecordVO, rejectRecord, nullPropertyNames);
        BeanUtils.copyProperties(rejectRecordVO, supplierInfo, nullPropertyNames);
        List<RejectRecord> storageRecordList = rejectRecordService.findRecordList(rejectRecord, supplierInfo);
        List<RejectRecordVO> collect = storageRecordList.stream().map(e -> {
            RejectRecordVO temp = new RejectRecordVO();
            BeanUtils.copyProperties(e, temp);
            SupplierInfo info= supplierService.findByPrimaryKey(e.getSupplierNo());
            temp.setSupplierName(info.getName());
            temp.setIndustryType(info.getIndustry());
            temp.setSupplierPhoneNo(info.getPhone());
            temp.setSupplierType(info.getType());
            return temp;
        }).collect(Collectors.toList());
        page.addAll(collect);
        page.setTotal( rejectRecordService.findRecordListSize(rejectRecord, supplierInfo));
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveRejectRecord(RejectRecordSaveVO rejectRecordSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(rejectRecordSaveVO);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(rejectRecordSaveVO, rejectRecord, nullPropertyNames);
        final List<RejectSuppliesInfo> list = rejectRecordSaveVO.getStockDetailList().stream().filter(e->StringUtils.isNotBlank(e.getProductType())).collect(Collectors.toList());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String creater = "admin";
        Date now = new Date();
        List<RejectRecord> toBeSavedRejectRecordList=new ArrayList<>();
        List<RejectSuppliesInfo> toBeInsertRejectSuppliesList=new ArrayList<>();
        if (StringUtils.isBlank(rejectRecord.getRecordNo())) {
            Map<String, List<RejectSuppliesInfo>> map = list.stream().collect(Collectors.groupingBy(RejectSuppliesInfo::getProductType));

            Iterator<Map.Entry<String, List<RejectSuppliesInfo>>> iterator = map.entrySet().iterator();
            int i = 0;
            while(iterator.hasNext()){
                Map.Entry<String, List<RejectSuppliesInfo>> next = iterator.next();

                RejectRecord tmp = new RejectRecord();
                String recordNo =new StringBuffer(LocalDateTime.now().format(dateTimeFormatter)).append(StringUtils.leftPad(String.valueOf(++i),6,"0")).toString();
                tmp.setRecordNo(recordNo );
                tmp.setProductCount(next.getValue().size());
                tmp.setRejectedDate(now);
                tmp.setSupplierNo(rejectRecordSaveVO.getSupplierNo());
                tmp.setProductType(next.getKey());
                tmp.setCreateUser(creater);
                tmp.setUpdateUser(creater);
                tmp.setCreateDate(now);
                tmp.setUpdateDate(now);
                next.getValue().stream().forEachOrdered(e->{
                    e.setRecordNo(recordNo);
                    e.setCreateUser(creater);
                    e.setUpdateUser(creater);
                    e.setCreateDate(now);
                    e.setUpdateDate(now);
                    toBeInsertRejectSuppliesList.add(e);
                });
                toBeSavedRejectRecordList.add(tmp);
            }
        } else {
            String recordNo = rejectRecord.getRecordNo();
            rejectRecord = rejectRecordService.findByPrimaryKey(recordNo);
            Stream<String> distinct = list.stream().map(e -> e.getProductType()).distinct();
            long count =distinct.count();
            if (count>1){
                throw new IllegalArgumentException("修改时不能新增产品型号!");
            }
            String type = distinct.findAny().orElse(null);
            if (StringUtils.equals(rejectRecord.getProductType(),type)){
                throw new IllegalArgumentException("不能修改产品型号!");
            }
            rejectRecord.setProductCount(list.size());
            rejectRecord.setUpdateUser(creater);
            rejectRecord.setUpdateDate(now);
            list.stream().filter(e->StringUtils.isNoneBlank(e.getProductType())).forEach(e->{
                e.setRecordNo(recordNo);
                e.setCreateUser(creater);
                e.setUpdateUser(creater);
                e.setCreateDate(now);
                e.setUpdateDate(now);
                toBeInsertRejectSuppliesList.add(e);
            });
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
