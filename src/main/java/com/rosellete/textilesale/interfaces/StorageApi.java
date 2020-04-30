package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.StoragePackageVO;
import com.rosellete.textilesale.vo.StorageRecordVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface StorageApi {
    @PostMapping(value = "/queryStorageRecordList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getStorageRecordList(@RequestBody @Valid StorageRecordVO storageRecordVO);

    @PostMapping(value = "/saveStorageRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveStorageRecord(@RequestBody @Valid StorageRecordVO storageRecordVO);

    @GetMapping(path = "/viewStorageDetail")
    RestResponse getStoragePackage(@RequestParam("recordNo") String recordNo);

    @PostMapping(value = "/queryStoragePackageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getStoragePackageList(@RequestBody @Valid StoragePackageVO storagePackageVO);

    @GetMapping(path = "/viewPackageInventory")
    RestResponse getPackageInventory(@RequestParam("recordNo") String recordNo,
                                     @RequestParam("packageNo") String packageNo);

    @PostMapping(value = "/savePackageInventory", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse savePackageInventoryList(@RequestBody @Valid StoragePackageVO storagePackageVO);
}
