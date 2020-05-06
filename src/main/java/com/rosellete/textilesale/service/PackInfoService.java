package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderStockDetailInfoDao;
import com.rosellete.textilesale.dao.PackDetailInfoDao;
import com.rosellete.textilesale.dao.PackInfoDao;
import com.rosellete.textilesale.model.PackDetailInfo;
import com.rosellete.textilesale.model.PackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PackInfoService {
    @Autowired
    private PackInfoDao packInfoDao;

    @Autowired
    private PackDetailInfoDao packDetailInfoDao;

    @Autowired
    private OrderStockDetailInfoDao orderStockDetailInfoDao;

    public int getMaxPackNo(String orderNo){
        return packInfoDao.getMaxPackNo(orderNo);
    }

    public int savePackInfo(PackInfo packInfo){
        PackInfo result = packInfoDao.save(packInfo);
        return result!=null ? 1: 0;
    }
    public int savePackDetailInfo(List<PackDetailInfo> packDetailInfos){
        List<PackDetailInfo> result = packDetailInfoDao.saveAll(packDetailInfos);
        return result!=null ? 1: 0;
    }

    public int updatePackInfo(PackInfo packInfo){
        PackInfo result = packInfoDao.save(packInfo);
        return result!=null ? 1: 0;
    }

    public int deletePackInfoById(String id){
        List<PackDetailInfo> detailInfos = packDetailInfoDao.getPackDetailsByPackId(id);
        for (PackDetailInfo packDetailInfo : detailInfos){
            orderStockDetailInfoDao.updateStatusById("0",packDetailInfo.getStockDetailId());
        }
        packDetailInfoDao.deleteDetailByPackId(id);
        return packInfoDao.deletePackById(id);
    }
    public List<Map<String,Object>> getPackDetailList(String orderNo,String packId){
        return packDetailInfoDao.getProductMap(orderNo,packId);
    }
    public List<Map<String,Object>> getPackLengthList(String packId,String productType){
        return packDetailInfoDao.getPackDetailRices(packId,productType);
    }

    public PackInfo getPackInfoById(String id){
        return packInfoDao.getPackInfoById(id);
    }
}
