package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.PackInfoBusiness;
import com.rosellete.textilesale.model.PackDetailInfo;
import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.service.OrderStockDetailInfoService;
import com.rosellete.textilesale.service.PackDetailInfoService;
import com.rosellete.textilesale.service.PackInfoService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PackInfoBusinessImpl implements PackInfoBusiness {

    @Autowired
    private PackInfoService packInfoService;

    @Autowired
    private OrderStockDetailInfoService orderStockDetailInfoService;

    @Override
    public RestResponse getPackDetails(String id) {
        PackDetailInfoVO packDetailInfoVO = new PackDetailInfoVO();
        PackInfo packInfo = packInfoService.getPackInfoById(id);
        packDetailInfoVO.setProductCount(packInfo.getProductCount());
        packDetailInfoVO.setPieceCount(packInfo.getPieceCount());
        packDetailInfoVO.setRiceCount(packInfo.getRiceCount());
        packDetailInfoVO.setPackNo(packInfo.getPackNo());
        List<Map<String,Object>> prodMaps = packInfoService.getPackDetailList(packInfo.getOrderNo(),packInfo.getId());
        List<PackInfoVO> packInfoVOS = new ArrayList<>();
        for (Map<String,Object> map : prodMaps) {
            PackInfoVO packInfoVO = new PackInfoVO();
            packInfoVO.setColthModel((String)map.get("productType"));
            packInfoVO.setPicurl((String)map.get("prodPic"));
            packInfoVO.setPieceOptions(packInfoService.getPackLengthList(packInfo.getId(),packInfoVO.getColthModel()));
            packInfoVOS.add(packInfoVO);
        }
        packDetailInfoVO.setPackInfoVOS(packInfoVOS);
        return new RestResponse(packDetailInfoVO);
    }

    @Override
    public RestResponse savePackInfo(PackDetailInfoVO packDetailInfoVO) {
        PackInfo packInfo = new PackInfo();
        packInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        packInfo.setOrderNo(packDetailInfoVO.getOrderNo());
        packInfo.setProductCount(packDetailInfoVO.getProductCount());
        packInfo.setPieceCount(packDetailInfoVO.getPieceCount());
        packInfo.setRiceCount(packDetailInfoVO.getRiceCount());
        packInfo.setStatus(0);
        packInfo.setCreateUser("");
        int maxNo = packInfoService.getMaxPackNo(packInfo.getOrderNo());
        packInfo.setPackNo(maxNo+1);
        packInfoService.savePackInfo(packInfo);
        List<PackDetailInfo> detailList = new ArrayList<>();
        for (Map<String,Object> map : packDetailInfoVO.getProductInfoList()){
            PackDetailInfo packDetailInfo = new PackDetailInfo();
            packDetailInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            packDetailInfo.setOrderNo(packDetailInfoVO.getOrderNo());
            packDetailInfo.setPackId(packInfo.getId());
            packDetailInfo.setProductType((String)map.get("productType"));
            packDetailInfo.setProdPic((String)map.get("picurl"));
            packDetailInfo.setStockLength(Double.valueOf((Integer)map.get("stockLength")));
            packDetailInfo.setStockDetailId(String.valueOf((Integer)map.get("id")));
            packDetailInfo.setStatus(0);
            packDetailInfo.setCreateUser("");
            detailList.add(packDetailInfo);
            orderStockDetailInfoService.updateStatusById("1",packDetailInfo.getStockDetailId());
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
            packInfoService.updatePackInfo(packInfo);
        }
        return new RestResponse();
    }

    @Override
    public RestResponse deletePackInfoById(String id) {
        packInfoService.deletePackInfoById(id);
        return new RestResponse();
    }
}
