package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerDao extends BaseRepository<CustomerInfo, Integer>, PagingAndSortingRepository<CustomerInfo, Integer> {
    @Query(value = "SELECT a.customer_no AS customerNo,a.NAME AS customerName, a.phone AS customerPhoneNo, '2' AS storageType, a.type AS customerType," +
            " a.industry AS industryType, a.address AS customerAddress, a.vip AS vip " +
            "FROM  t_customer_info a ORDER BY a.vip", nativeQuery = true)
    List<Map<String, String>> findAllOrderByVip();


}