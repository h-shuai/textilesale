package com.rosellete.textilesale.util;

import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.vo.ConsignorVO;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import com.rosellete.textilesale.vo.SupplierInfoVO;

public class SupplierAndCustomerConvertorUtil {
    public static void convertConsignor2SupplierInfo(ConsignorVO consignorVO, SupplierInfo supplierInfo) {
        supplierInfo.setSupplierNo(consignorVO.getConsignorNo());
        supplierInfo.setName(consignorVO.getConsignor());
        supplierInfo.setPhone(consignorVO.getConsignorPhoneNo());
        supplierInfo.setType(consignorVO.getConsignorType());
        supplierInfo.setIndustry(consignorVO.getIndustryType());
        supplierInfo.setAddress(consignorVO.getConsignorAddress());
    }

    public static void convertSupplierInfo2Consignor(SupplierInfo supplierInfo, ConsignorVO consignorVO) {
        consignorVO.setConsignorNo(supplierInfo.getSupplierNo());
        consignorVO.setConsignor(supplierInfo.getName());
        consignorVO.setConsignorType(supplierInfo.getType());
        consignorVO.setIndustryType(supplierInfo.getIndustry());
        consignorVO.setStorageType("1");
        consignorVO.setConsignorPhoneNo(supplierInfo.getPhone());
        consignorVO.setConsignorAddress(supplierInfo.getAddress());
        consignorVO.setPriority(supplierInfo.getVip());
    }

    public static void convertConsignor2CustomerInfo(ConsignorVO consignorVO, CustomerInfo customerInfo) {
        customerInfo.setCustomerNo(consignorVO.getConsignorNo());
        customerInfo.setName(consignorVO.getConsignor());
        customerInfo.setPhone(consignorVO.getConsignorPhoneNo());
        customerInfo.setType(consignorVO.getConsignorType());
        customerInfo.setIndustry(consignorVO.getIndustryType());
        customerInfo.setAddress(consignorVO.getConsignorAddress());
    }

    public static void convertCustomerInfo2Consignor(CustomerInfo customerInfo, ConsignorVO consignorVO) {
        consignorVO.setConsignorNo(customerInfo.getCustomerNo());
        consignorVO.setConsignor(customerInfo.getName());
        consignorVO.setConsignorType(customerInfo.getType());
        consignorVO.setIndustryType(customerInfo.getIndustry());
        consignorVO.setStorageType("2");
        consignorVO.setConsignorPhoneNo(customerInfo.getPhone());
        consignorVO.setConsignorAddress(customerInfo.getAddress());
        consignorVO.setPriority(customerInfo.getVip());
    }

    public static void convertSupplierInfo2VO(SupplierInfo supplierInfo, SupplierInfoVO supplierInfoVO) {
        supplierInfoVO.setSupplierNo(supplierInfo.getSupplierNo());
        supplierInfoVO.setSupplierName(supplierInfo.getName());
        supplierInfoVO.setSupplierType(supplierInfo.getType());
        supplierInfoVO.setIndustryType(supplierInfo.getIndustry());
        supplierInfoVO.setSupplierPhoneNo(supplierInfo.getPhone());
        supplierInfoVO.setSupplierAddress(supplierInfo.getAddress());
        supplierInfoVO.setPriority(supplierInfo.getVip());
    }

    public static void convertSupplierVO2Info(SupplierInfoVO supplierInfoVO, SupplierInfo supplierInfo) {
        supplierInfo.setSupplierNo(supplierInfoVO.getSupplierNo());
        supplierInfo.setName(supplierInfoVO.getSupplierName());
        supplierInfo.setPhone(supplierInfoVO.getSupplierPhoneNo());
        supplierInfo.setType(supplierInfoVO.getSupplierType());
        supplierInfo.setIndustry(supplierInfoVO.getIndustryType());
        supplierInfo.setAddress(supplierInfoVO.getSupplierAddress());
    }

    public static void convertCustomerVO2Info(CustomerInfoVO customerInfoVO, CustomerInfo customerInfo) {
        customerInfo.setCustomerNo(customerInfoVO.getCustomerNo());
        customerInfo.setName(customerInfoVO.getCustomerName());
        customerInfo.setPhone(customerInfoVO.getCustomerPhoneNo());
        customerInfo.setType(customerInfoVO.getCustomerType());
        customerInfo.setIndustry(customerInfoVO.getIndustryType());
        customerInfo.setAddress(customerInfoVO.getCustomerAddress());
    }

    public static void convertCustomerInfo2VO(CustomerInfo customerInfo, CustomerInfoVO customerInfoVO) {
        customerInfoVO.setCustomerNo(customerInfo.getCustomerNo());
        customerInfoVO.setCustomerName(customerInfo.getName());
        customerInfoVO.setCustomerType(customerInfo.getType());
        customerInfoVO.setIndustryType(customerInfo.getIndustry());
        customerInfoVO.setCustomerPhoneNo(customerInfo.getPhone());
        customerInfoVO.setCustomerAddress(customerInfo.getAddress());
        customerInfoVO.setPriority(customerInfo.getVip());
    }
}
