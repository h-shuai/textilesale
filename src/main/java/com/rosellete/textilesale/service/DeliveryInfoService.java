package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.DeliveryInfoDao;
import com.rosellete.textilesale.model.DeliveryInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryInfoService {
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;

    public int getTodayDepartNum(){
        return deliveryInfoDao.getTodayDepartNum();
    }

    public List<String> saveDeliveryInfo(List<DeliveryInfo> deliveryInfos){
        List<DeliveryInfo> returnInfos = deliveryInfoDao.saveAll(deliveryInfos);
        List<String> returnStrs = new ArrayList<>();
        for (DeliveryInfo deliveryInfo : returnInfos) {
            returnStrs.add(deliveryInfo.getId());
        }
        return returnStrs;
    }

    public RestResponse updDeliveryInfo(DeliveryInfoVO deliveryInfoVO){
        List<DeliveryInfo> infos = deliveryInfoDao.findAllById(deliveryInfoVO.getIds());
        infos.parallelStream().forEach(item -> item.setRecerveFileUrl(deliveryInfoVO.getFileNames()));
        deliveryInfoDao.saveAll(infos);
        return new RestResponse();
    }
}
