package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.StorageBusiness;
import com.rosellete.textilesale.interfaces.StorageApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderDetailInfoVO;
import com.rosellete.textilesale.vo.PackageInventoryInfoVO;
import com.rosellete.textilesale.vo.StoragePackageVO;
import com.rosellete.textilesale.vo.StorageRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/storage")
public class StorageController implements StorageApi {
    @Autowired
    private StorageBusiness storageBusiness;

    @Override
    public RestResponse getStorageRecordList(@RequestBody StorageRecordVO storageRecordVO) {
        PageInfo<StorageRecordVO> pageInfo=storageBusiness.getStorageRecordList(storageRecordVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse saveStorageRecord(@RequestBody StorageRecordVO storageRecordVO) {
        RestResponse response = new RestResponse();
        try {
            storageBusiness.saveStorageRecord(storageRecordVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg("系统内部错误，请稍后重试");
            log.error("保存抄包记录数据{}失败", storageRecordVO, e);
        }
        return response;
    }

    @Override
    public RestResponse getStoragePackage(String recordNo) {
        PageInfo<StoragePackageVO> pageInfo = storageBusiness.getStoragePackage(recordNo);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getStoragePackageList(@RequestBody StoragePackageVO storagePackageVO) {
        PageInfo<StoragePackageVO> pageInfo = storageBusiness.getStoragePackageList(storagePackageVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getPackageInventory(String recordNo, String packageNo) {
        PageInfo<PackageInventoryInfoVO> pageInfo = storageBusiness.getPackageInventory(recordNo,packageNo);
        return new RestResponse(pageInfo);
    }
}
