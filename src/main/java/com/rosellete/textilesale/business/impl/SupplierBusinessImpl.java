package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.SupplierBusiness;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.service.SupplierService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.SupplierInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("supplierBusiness")
public class SupplierBusinessImpl implements SupplierBusiness {
    @Autowired
    private SupplierService supplierService;

    @Override
    public PageInfo<SupplierInfoVO> findAllSupplierWarehouseRelated() {
        List<Map<String, String>> allOrderByVip = supplierService.findAllOrderByVip();
        List<SupplierInfoVO> collect = allOrderByVip.stream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, SupplierInfoVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<SupplierInfoVO> findSupplierListWarehouseRelated(SupplierInfoVO supplierInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(supplierInfoVO);
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoVO, supplierInfo, nullOrBlankPropertyNames);
        Page<SupplierInfo> supplierList = supplierService.findSupplierList(supplierInfo);
        List<SupplierInfoVO> collect = supplierList.stream().map(e -> {
            SupplierInfoVO temp = new SupplierInfoVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        com.github.pagehelper.Page page = new com.github.pagehelper.Page(supplierInfoVO.getPageNum(), supplierInfoVO.getPageSize());
        page.setTotal(supplierList.getTotalElements());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Override
    public PageInfo<SupplierInfoVO> findAllSupplier() {
        List<SupplierInfoVO> collect = supplierService.findAllOrderByVip().stream().
                map(e -> {
                            String jsonString = JSON.toJSONString(e);
                            return JSONObject.parseObject(jsonString, SupplierInfoVO.class);
                        }
                ).collect(Collectors.toList());
        List<SupplierInfoVO> sorted = collect.stream().sorted(Comparator.comparing(SupplierInfoVO::getVip)).
                collect(Collectors.toList());
        return new PageInfo<>(sorted);
    }

    @Override
    public Page<SupplierInfo> findSupplier(SupplierInfoVO supplierInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(supplierInfoVO);
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoVO, supplierInfo, nullOrBlankPropertyNames);
        return supplierService.findSupplierList(supplierInfo);
    }

    @Override
    public void save(SupplierInfoVO supplierInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(supplierInfoVO);
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoVO, supplierInfo, nullOrBlankPropertyNames);
        supplierService.save(supplierInfo);
    }

    @Override
    public SupplierInfo findBySupplierNo(int supplierNo) {

        return supplierService.findByPrimaryKey(supplierNo);
    }
}
