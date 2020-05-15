package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.SupplierInfo;

import java.util.List;

public interface SupplierDao extends BaseRepository<SupplierInfo, Integer> {
    List<SupplierInfo> getAllByVip(String vip);

    List<SupplierInfo> findAllByOrderByVipDesc();

    List<SupplierInfo> findAllByOrderByVipAsc();


}