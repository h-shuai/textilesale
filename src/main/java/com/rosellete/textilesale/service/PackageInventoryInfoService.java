package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.PackageInventoryInfoDao;
import com.rosellete.textilesale.model.PackageInventoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageInventoryInfoService {

    @Autowired
    private PackageInventoryInfoDao packageInventoryInfoDao;

    public List<PackageInventoryInfo> findPackageInventoryByPackageNo(String packageNo) {
        return packageInventoryInfoDao.findByPackageNo(packageNo);
    }

    public void savePackageInventoryList(List<PackageInventoryInfo> inventoryInfoList) {
        packageInventoryInfoDao.saveAll(inventoryInfoList);
    }

    public List<String> findAllProductType() {
      return packageInventoryInfoDao.findAllProductType();
    }

    public List<PackageInventoryInfo> findStoredInventoryBySupplierNo(Integer supplierNo) {
      return packageInventoryInfoDao.findAllBySupplierNo(supplierNo);
    }
}
