package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PackageInventoryInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageInventoryInfoDao extends BaseRepository<PackageInventoryInfo, Long> {
    @Query(value = "SELECT t.* FROM t_package_inventory_info t where t.package_no= ?1", nativeQuery = true)
    List<PackageInventoryInfo> findByPackageNo(String packageNo);
}
