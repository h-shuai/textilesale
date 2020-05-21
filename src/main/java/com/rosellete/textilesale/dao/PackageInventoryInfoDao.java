package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PackageInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageInventoryInfoDao extends BaseRepository<PackageInventory, Long> {
    @Query(value = "SELECT t.* FROM t_package_inventory_info t where t.package_no= ?1", nativeQuery = true)
    List<PackageInventory> findByPackageNo(String packageNo);

    @Query(value = "SELECT distinct t.product_type FROM t_package_inventory_info t ", nativeQuery = true)
    List<String> findAllProductType();

    @Query(value = "SELECT t.* FROM t_package_inventory_info t where t.package_no in (" +
            " select b.package_no from t_storage_record a,t_storage_package_info b " +
            "where a.record_no =b.record_no and a.consignor_no =?1 )", nativeQuery = true)
    List<PackageInventory> findAllBySupplierNo(Integer supplierNo);
}
