package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SupplierDao extends BaseRepository<SupplierInfo, Integer> {
    @Query(value = "SELECT a.supplier_no AS supplierNo,a.NAME AS supplierName, a.phone AS supplierPhoneNo, '1' AS storageType, a.type AS supplierType," +
                   " a.industry AS industryType, a.address AS supplierAddress, a.vip AS priority " +
                   "FROM  t_supplier_info a ORDER BY a.vip", nativeQuery = true)
    List<Map<String, String>> findAllOrderByVip();


}