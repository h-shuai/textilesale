package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.PackageInventoryInfoDao;
import com.rosellete.textilesale.model.PackageInventoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageInventoryInfoService {

    @Autowired
    private PackageInventoryInfoDao packageInventoryInfoDao;

    public List<PackageInventoryInfo> findPackageInventoryByPackageNo(String packageNo) {
        return packageInventoryInfoDao.findByPackageNo(packageNo);
    }
}
