package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RejectBusiness;
import com.rosellete.textilesale.model.RejectRecord;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import com.rosellete.textilesale.service.RejectRecordService;
import com.rosellete.textilesale.service.RejectSuppliesInfoService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("rejectBusiness")
public class RejectBusinessImpl implements RejectBusiness {
    @Autowired
    private RejectRecordService rejectRecordService;
    @Autowired
    private RejectSuppliesInfoService rejectSuppliesInfoService;

    @Override
    public PageInfo<RejectRecordVO> getRejectRecordList(RejectRecordVO rejectRecordVO) {
        Page page = new Page(rejectRecordVO.getPageNum(), rejectRecordVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(rejectRecordVO);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(rejectRecordVO, rejectRecord, nullPropertyNames);
        List<RejectRecord> storageRecordList;
        Date startDate = rejectRecordVO.getStartDate(), endDate = rejectRecordVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            storageRecordList = rejectRecordService.findRecordList(rejectRecord, startDate, endDate);
        } else {
            storageRecordList = rejectRecordService.findRecordList(rejectRecord, null, null);
        }
        List<RejectRecordVO> collect = storageRecordList.stream().map(e -> {
            RejectRecordVO temp = new RejectRecordVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveRejectRecord(RejectRecordSaveVO rejectRecordSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(rejectRecordSaveVO);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(rejectRecordSaveVO, rejectRecord, nullPropertyNames);
        List<RejectSuppliesInfo> list = rejectRecordSaveVO.getStockDetailList();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
        String recordNo;
        String creater = "admin";
        Date now = new Date();
        if (StringUtils.isBlank(rejectRecord.getRecordNo())) {
            recordNo = LocalDateTime.now().format(dateTimeFormatter);
            rejectRecord.setCreateUser(creater);
            rejectRecord.setCreateDate(now);

        } else {
            recordNo = rejectRecord.getRecordNo();
            rejectRecord = rejectRecordService.findByPrimaryKey(recordNo);
        }
        String finalRecordNo = recordNo;
        List<RejectSuppliesInfo> collect = list.stream().map(e -> {
            e.setRecordNo(finalRecordNo);
            e.setCreateUser(creater);
            e.setUpdateUser(creater);
            e.setCreateDate(now);
            e.setUpdateDate(now);
            return e;
        }).collect(Collectors.toList());
        rejectRecord.setRejectedDate(now);
        rejectRecord.setUpdateUser(creater);
        rejectRecord.setUpdateDate(now);
        rejectSuppliesInfoService.deleteRejectSuppliesByRecordNo(recordNo);
        rejectSuppliesInfoService.saveRejectSupplies(collect);
        rejectRecordService.saveRejectRecord(rejectRecord);
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
