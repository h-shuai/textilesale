package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.SupplierDao;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    private SupplierDao supplierDao;

    public void save(SupplierInfo supplierInfo) {
        supplierDao.save(supplierInfo);
    }

    public List<SupplierInfo> getAllByVip() {
        return null;//supplierDao.findAllByOrOrderByVip();
    }

    public List<Map<String, String>> findAllOrderByVip() {
        return supplierDao.findAllOrderByVip();
    }

    public List<SupplierInfo> findAllOrdered() {
        return supplierDao.findAll(Sort.by("vip"));
    }

    public List<SupplierInfo> findSupplierList(SupplierInfo supplierInfo) {
        Example<SupplierInfo> example = Example.of(supplierInfo);
        return supplierDao.findAll(example,Sort.by("vip"));
    }

}
