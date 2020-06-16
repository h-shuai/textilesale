package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.PackageInventoryInfoDao;
import com.rosellete.textilesale.model.PackageInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageInventoryInfoService {

    @Autowired
    private PackageInventoryInfoDao packageInventoryInfoDao;

    public List<PackageInventory> findPackageInventoryByPackageNo(String packageNo) {
        return packageInventoryInfoDao.findByPackageNo(packageNo);
    }

    public void savePackageInventoryList(List<PackageInventory> inventoryInfoList) {
        packageInventoryInfoDao.saveAll(inventoryInfoList);
    }

    public List<String> findAllProductType() {
        return packageInventoryInfoDao.findAllProductType();
    }

    public List<PackageInventory> findStoredInventoryBySupplierNo(Integer supplierNo) {
        return packageInventoryInfoDao.findAllBySupplierNo(supplierNo);
    }

    public String findLatestImageNameByProductType(String productType) {
        PackageInventory info = new PackageInventory();
        info.setProductType(productType);
        Example<PackageInventory> example = Example.of(info);
        info = packageInventoryInfoDao.findAll(example, Sort.by("createDate").descending()).parallelStream().findFirst().orElse(null);
        return null == info ? null : info.getImageName();
    }
}
