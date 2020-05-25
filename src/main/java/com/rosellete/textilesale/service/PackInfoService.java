package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrderStockDetailInfoDao;
import com.rosellete.textilesale.dao.PackDetailInfoDao;
import com.rosellete.textilesale.dao.PackInfoDao;
import com.rosellete.textilesale.model.PackDetailInfo;
import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.vo.PackInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private RejectSuppliesStockDetailService rejectSuppliesStockDetailService;

    public List<PackInfo> getPackListByCustomer(String customer,String status,String businessType){
        return packInfoDao.getPackListByCustomer(customer,status,businessType);
    }

    public int getMaxPackNo(String customer){
        return packInfoDao.getMaxPackNo(customer);
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
        PackInfo packInfo = packInfoDao.getPackInfoById(id);
        List<PackDetailInfo> detailInfos = packDetailInfoDao.getPackDetailsByPackId(id);
        List<String> ids = new ArrayList<>();
        for (PackDetailInfo packDetailInfo : detailInfos){
            ids.add(packDetailInfo.getStockDetailId());
        }
        if (packInfo.getBusinessType().equals("0")) {
            orderStockDetailInfoDao.updateStatusByIds("0",ids);
        } else {
            rejectSuppliesStockDetailService.updateStatusByIds("0",ids);
        }
        packDetailInfoDao.deleteDetailByPackId(id);
        return packInfoDao.deletePackById(id);
    }
    public List<Map<String,Object>> getPackDetailList(String packId){
        return packDetailInfoDao.getProductMap(packId);
    }
    public List<Map<String,Object>> getPackLengthList(String packId,String productType,String orderNo){
        return packDetailInfoDao.getPackDetailRices(packId,productType,orderNo);
    }

    public PackInfo getPackInfoById(String id){
        return packInfoDao.getPackInfoById(id);
    }

    public List<PackInfoVO> getWaitDeliveryList(PackInfo packInfo) {
        List<Map<String, Object>> maps = packInfoDao.getWaitDeliveryList(packInfo.getCustomerName());
        List<PackInfoVO> packInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            PackInfoVO packInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), PackInfoVO.class);
            packInfoVOList.add(packInfoVO);
        }
        return packInfoVOList;
    }

    public void updateStatusByIds(List<String> ids){
        packInfoDao.updateStatusByIds(ids);
    }
}
