package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.StorageBusiness;
import com.rosellete.textilesale.model.PackageInventoryInfo;
import com.rosellete.textilesale.model.StoragePackageInfo;
import com.rosellete.textilesale.model.StorageRecord;
import com.rosellete.textilesale.service.PackageInventoryInfoService;
import com.rosellete.textilesale.service.StoragePackageInfoService;
import com.rosellete.textilesale.service.StorageRecordService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.PackageInventoryInfoVO;
import com.rosellete.textilesale.vo.StoragePackageVO;
import com.rosellete.textilesale.vo.StorageRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        PageHelper.startPage(storageRecordVO.getPageNum(), storageRecordVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storageRecordVO);
        StorageRecord storageRecord = new StorageRecord();
        BeanUtils.copyProperties(storageRecordVO, storageRecord, nullPropertyNames);
        List<StorageRecord> storageRecordList;
        Date startDate = storageRecordVO.getStartDate(), endDate = storageRecordVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            storageRecordList = storageRecordService.getStorageRecordList(storageRecord, startDate, endDate);
        } else {
            storageRecordList = storageRecordService.getStorageRecordList(storageRecord, null, null);
        }
        List<StorageRecordVO> collect = storageRecordList.stream().map(e -> {
            StorageRecordVO temp = new StorageRecordVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void saveStorageRecord(StorageRecordVO storageRecordVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storageRecordVO);
        StorageRecord storageRecord = new StorageRecord();
        BeanUtils.copyProperties(storageRecordVO, storageRecord, nullPropertyNames);
        int quantity = storageRecord.getPackageQuantity();
        if (quantity > 0) {
            Date now = new Date();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
            String recordNo = LocalDateTime.now().format(dateTimeFormatter);
            String creator = "admin";
            List<StoragePackageInfo> packageList = new ArrayList<>(quantity);
            StoragePackageInfo temp;
            for (int i = 0; i < quantity; i++) {
                temp = new StoragePackageInfo();
                temp.setRecordNo(recordNo);
                temp.setPackageNo(recordNo.concat("#").concat(StringUtils.leftPad(String.valueOf(1 + i), 3, '0')));
                temp.setCreateUser(creator);
                temp.setCreateDate(now);
                temp.setUpdateUser(creator);
                temp.setUpdateDate(now);
                packageList.add(temp);
            }
            storageRecord.setRecordNo(recordNo);
            storageRecord.setStorageDate(now);
            storageRecord.setCreateUser(creator);
            storageRecord.setCreateDate(now);
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
    public PageInfo<StoragePackageVO> getStoragePackageList(StoragePackageVO storagePackageVO) {
        PageHelper.startPage(storagePackageVO.getPageNum(), storagePackageVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(storagePackageVO);
        StoragePackageInfo storagePackageInfo = new StoragePackageInfo();
        BeanUtils.copyProperties(storagePackageVO, storagePackageInfo, nullPropertyNames);
        List<Map<String, Object>> resultSetList;
        Date startDate = storagePackageVO.getStartDate(), endDate = storagePackageVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            resultSetList = storagePackageInfoService.findPackageList(storagePackageInfo.getRecordNo(), storagePackageInfo.getPackageNo(),
                    storagePackageVO.getStorageType(), storagePackageVO.getStorageClerkName(), storagePackageVO.getConsignorPhoneNo(),
                    storagePackageVO.getConsignor(), storagePackageVO.getConsignorType(), storagePackageVO.getIndustryType(), startDate, endDate);
        } else {
            resultSetList = storagePackageInfoService.findPackageList(storagePackageInfo.getRecordNo(), storagePackageInfo.getPackageNo(),
                    storagePackageVO.getStorageType(), storagePackageVO.getStorageClerkName(), storagePackageVO.getConsignorPhoneNo(),
                    storagePackageVO.getConsignor(), storagePackageVO.getConsignorType(), storagePackageVO.getIndustryType(), null, null);
        }
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
}
