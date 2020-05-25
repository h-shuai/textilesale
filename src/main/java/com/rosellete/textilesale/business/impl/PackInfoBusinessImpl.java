package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.PackInfoBusiness;
import com.rosellete.textilesale.model.PackDetailInfo;
import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.service.OrderStockDetailInfoService;
import com.rosellete.textilesale.service.PackInfoService;
import com.rosellete.textilesale.service.RejectSuppliesStockDetailService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import com.rosellete.textilesale.vo.PackSubInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackInfoBusinessImpl implements PackInfoBusiness {

    @Autowired
    private PackInfoService packInfoService;

    @Autowired
    private OrderStockDetailInfoService orderStockDetailInfoService;

    @Autowired
    private RejectSuppliesStockDetailService rejectSuppliesStockDetailService;

    @Override
    public RestResponse getPackListByCustomer(String customer,String businessType) {
        return new RestResponse(packInfoService.getPackListByCustomer(customer,"1",businessType));
    }

    @Override
    public RestResponse getPackDetails(String id) {
        PackDetailInfoVO packDetailInfoVO = new PackDetailInfoVO();
        PackInfo packInfo = packInfoService.getPackInfoById(id);
        packDetailInfoVO.setProductCount(packInfo.getProductCount());
        packDetailInfoVO.setPieceCount(packInfo.getPieceCount());
        packDetailInfoVO.setRiceCount(packInfo.getRiceCount());
        packDetailInfoVO.setPackNo(packInfo.getPackNo());
        packDetailInfoVO.setStatus(packInfo.getStatus());
        packDetailInfoVO.setRemark(packInfo.getRemark());
        List<Map<String,Object>> prodMaps = packInfoService.getPackDetailList(packInfo.getId());
        List<String> oldOrderNos = new ArrayList<>();
        for (Map<String,Object> map : prodMaps){
            oldOrderNos.add((String)map.get("orderNo"));
        }
        List<String> newOrderNos = oldOrderNos.stream().distinct().collect(Collectors.toList());
        List<PackInfoVO> packInfoVOS = new ArrayList<>();
        for (String orderNo : newOrderNos){
            List<PackSubInfoVO> packSubInfoVOS = new ArrayList<>();
            PackInfoVO packInfoVO = new PackInfoVO();
            packInfoVO.setOrderNo(orderNo);
            for (Map<String,Object> map : prodMaps){
                if (orderNo.equals(map.get("orderNo"))){
                    PackSubInfoVO packSubInfoVO = new PackSubInfoVO();
                    String imageUrl;
                    if (StringUtils.isBlank((String)map.get("prodPic"))) {
                        imageUrl = "api/download/notfound.jpg";
                    } else {
                        imageUrl = new StringBuffer("api/download").append("/").append((String)map.get("prodPic")).toString();
                    }
                    packSubInfoVO.setPicurl(imageUrl);
                    packSubInfoVO.setColthModel((String)map.get("productType"));
                    packSubInfoVO.setPieceOptions(packInfoService.getPackLengthList(packInfo.getId(),packSubInfoVO.getColthModel(),packInfoVO.getOrderNo()));
                    packSubInfoVOS.add(packSubInfoVO);
                }
            }
            packInfoVO.setPackSubInfoVOS(packSubInfoVOS);
            packInfoVOS.add(packInfoVO);
        }
        packDetailInfoVO.setPackInfoVOS(packInfoVOS);
        return new RestResponse(packDetailInfoVO);
    }

    @Override
    public RestResponse savePackInfo(PackDetailInfoVO packDetailInfoVO) {
        PackInfo packInfo = new PackInfo();
        packInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        packInfo.setCustomerName(packDetailInfoVO.getCustomerName());
        packInfo.setCustomerId(packDetailInfoVO.getCustomerNo());
        packInfo.setBusinessType(packDetailInfoVO.getBusinessType());
        packInfo.setConsignDepId(packDetailInfoVO.getConsignDep());
        packInfo.setProductCount(packDetailInfoVO.getProductCount());
        packInfo.setPieceCount(packDetailInfoVO.getPieceCount());
        packInfo.setRiceCount(packDetailInfoVO.getRiceCount());
        packInfo.setStatus(0);
        packInfo.setCreateTime(new Date());
        packInfo.setCreateUser("");
        int maxNo = packInfoService.getMaxPackNo(packInfo.getCustomerId());
        packInfo.setPackNo(maxNo+1);
        packInfoService.savePackInfo(packInfo);
        List<PackDetailInfo> detailList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (Map<String,Object> map : packDetailInfoVO.getProductInfoList()){
            PackDetailInfo packDetailInfo = new PackDetailInfo();
            packDetailInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            if (packDetailInfoVO.getBusinessType().equals("0")) {
                packDetailInfo.setOrderNo(orderStockDetailInfoService.getStockDetailById(String.valueOf(map.get("id"))).getOrderNo());
            } else {
                packDetailInfo.setOrderNo(rejectSuppliesStockDetailService.getById(String.valueOf(map.get("id"))).getRecordNo());
            }
            packDetailInfo.setPackId(packInfo.getId());
            packDetailInfo.setProductType((String)map.get("productType"));
            packDetailInfo.setProdPic((String)map.get("picurl"));
            packDetailInfo.setStockLength(Double.valueOf((Integer)map.get("stockLength")));
            packDetailInfo.setStockDetailId(String.valueOf(map.get("id")));
            packDetailInfo.setStatus(0);
            packDetailInfo.setCreateTime(new Date());
            packDetailInfo.setCreateUser("");
            detailList.add(packDetailInfo);
            ids.add(packDetailInfo.getStockDetailId());
        }
        if (packDetailInfoVO.getBusinessType().equals("0")) {
            orderStockDetailInfoService.updateStatusByIds("1",ids);
        } else {
            rejectSuppliesStockDetailService.updateStatusByIds("1",ids);
        }
        packInfoService.savePackDetailInfo(detailList);
        return new RestResponse(packInfo);
    }

    @Override
    public RestResponse updatePackInfo(PackDetailInfoVO packDetailInfoVO) {
        PackInfo packInfo = packInfoService.getPackInfoById(packDetailInfoVO.getId());
        if (packInfo != null){
            packInfo.setStatus(packDetailInfoVO.getStatus()!=null?packDetailInfoVO.getStatus():packInfo.getStatus());
            packInfo.setPackPic(StringUtils.isNotBlank(packDetailInfoVO.getPackPic())?packDetailInfoVO.getPackPic():packInfo.getPackPic());
            packInfo.setRemark(packDetailInfoVO.getRemark());
            packInfo.setUpdateTime(new Date());
            packInfoService.updatePackInfo(packInfo);
        }
        return new RestResponse();
    }

    @Override
    public RestResponse deletePackInfoById(String id) {
        packInfoService.deletePackInfoById(id);
        return new RestResponse();
    }

    @Override
    public PagedListHolder<PackInfoVO> getWaitDeliveryList(PackInfo packInfo) {
        PagedListHolder<PackInfoVO> pagedListHolder = new PagedListHolder<>(packInfoService.getWaitDeliveryList(packInfo));
        pagedListHolder.setPage(packInfo.getPageNum() - 1);
        pagedListHolder.setPageSize(packInfo.getPageSize());
        return pagedListHolder;
    }
}
