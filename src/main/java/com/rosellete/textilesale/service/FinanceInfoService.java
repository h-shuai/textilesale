package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.FinanceDetailInfoDao;
import com.rosellete.textilesale.dao.FinanceLinkOrderDao;
import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.FinanceDetailInfo;
import com.rosellete.textilesale.model.FinanceLinkOrder;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinanceInfoService {
    @Autowired
    private FinanceLinkOrderDao financeLinkOrderDao;
    @Autowired
    private FinanceDetailInfoDao financeDetailInfoDao;
    @Autowired
    private OrderInfoDao orderInfoDao;

    public void saveFinanceInfo(FinanceInfoVO financeInfoVO){
        String batchNo = UUID.randomUUID().toString().replaceAll("-", "");
        List<FinanceDetailInfo> detailInfos = new ArrayList<>();
        List<FinanceLinkOrder> financeLinkOrders = new ArrayList<>();
        List<String> orders = new ArrayList<>();
        for (Map<String,Object> map : financeInfoVO.getAccountList()){
            FinanceDetailInfo financeDetailInfo = new FinanceDetailInfo();
            financeDetailInfo.setBatchNo(batchNo);
            financeDetailInfo.setPaymethodId(Long.valueOf(String.valueOf(map.get("paymentMethod"))));
            financeDetailInfo.setPayAmount(Double.valueOf((String)map.get("payAmount")));
            financeDetailInfo.setStatus("1");
            detailInfos.add(financeDetailInfo);
        }
        for (Map<String,Object> map : financeInfoVO.getSelectedRows()){
            FinanceLinkOrder financeLinkOrder = new FinanceLinkOrder();
            financeLinkOrder.setBatchNo(batchNo);
            financeLinkOrder.setOrderNo((String)map.get("orderNo"));
            financeLinkOrder.setCreateDate(new Date());
            financeLinkOrder.setStatus("1");
            financeLinkOrders.add(financeLinkOrder);
            orders.add((String)map.get("orderNo"));
        }
        financeDetailInfoDao.saveAll(detailInfos);
        orderInfoDao.updateSettleStatus(orders);
        financeLinkOrderDao.saveAll(financeLinkOrders);
    }
}
