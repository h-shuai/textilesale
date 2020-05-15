package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.SupplierDao;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierDao supplierDao;

    public void save(SupplierInfo supplierInfo){
        supplierDao.save(supplierInfo);
    }

    public List<SupplierInfo> getAllByVip(){
        return supplierDao.findAllByOrderByVipDesc();
    }
}
