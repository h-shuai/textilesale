package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.SupplierBusiness;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.service.SupplierService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.util.SupplierAndCustomerConvertorUtil;
import com.rosellete.textilesale.vo.SupplierInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        SupplierInfo supplierInfo = new SupplierInfo();
        SupplierAndCustomerConvertorUtil.convertSupplierVO2Info(supplierInfoVO, supplierInfo);
        List<SupplierInfo> supplierList = supplierService.findSupplierList(supplierInfo);
        List<SupplierInfoVO> collect = supplierList.stream().map(e -> {
            SupplierInfoVO temp = new SupplierInfoVO();
            SupplierAndCustomerConvertorUtil.convertSupplierInfo2VO(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<SupplierInfoVO> findAllSupplier() {
        List<SupplierInfoVO> collect = supplierService.findAllOrdered().stream().
                map(e -> {
                            SupplierInfoVO temp = new SupplierInfoVO();
                            SupplierAndCustomerConvertorUtil.convertSupplierInfo2VO(e, temp);
                            return temp;
                        }
                ).collect(Collectors.toList());
        List<SupplierInfoVO> sorted = collect.stream().sorted(Comparator.comparing(SupplierInfoVO::getPriority)).
                collect(Collectors.toList());
        return new PageInfo<>(sorted);
    }

    @Override
    public PageInfo<SupplierInfoVO> findSupplier(SupplierInfoVO supplierInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(supplierInfoVO);
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoVO, supplierInfo, nullOrBlankPropertyNames);
        List<SupplierInfoVO> collect = supplierService.findSupplierList(supplierInfo).stream().
                map(e -> {
                            SupplierInfoVO temp = new SupplierInfoVO();
                            SupplierAndCustomerConvertorUtil.convertSupplierInfo2VO(e, temp);
                            return temp;
                        }
                ).collect(Collectors.toList());
        List<SupplierInfoVO> sorted = collect.stream().sorted(Comparator.comparing(SupplierInfoVO::getPriority)).
                collect(Collectors.toList());
        return new PageInfo<>(sorted);
    }
}
