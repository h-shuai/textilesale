package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.DeliveryInfoDao;
import com.rosellete.textilesale.model.DeliveryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryInfoService {
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;

    public int getTodayDepartNum(){
        return deliveryInfoDao.getTodayDepartNum();
    }

    public void saveDeliveryInfo(List<DeliveryInfo> deliveryInfos){
        deliveryInfoDao.saveAll(deliveryInfos);
    }
}
