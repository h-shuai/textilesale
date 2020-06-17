package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.AccountDetailDao;
import com.rosellete.textilesale.dao.AccountMainDao;
import com.rosellete.textilesale.model.AccountDetail;
import com.rosellete.textilesale.model.AccountMain;
import com.rosellete.textilesale.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {
    @Autowired
    private AccountMainDao accountMainDao;
    @Autowired
    private AccountDetailDao accountDetailDao;

    public Page<AccountMain> getPageByCustomer(AccountVO accountVO) {
        Pageable pageable = PageRequest.of(accountVO.getPageNum()-1,accountVO.getPageSize());
        return accountMainDao.getPageByCustomer(accountVO.getCustomerName(),pageable);
    }

    public void saveAccountInfo(AccountVO accountVO) {
        AccountMain accountMain = accountMainDao.getAccountMainByCustomerNo(accountVO.getCustomerNo());
        if (accountMain == null) {
            accountMain = new AccountMain();
            accountMain.setCustomerNo(accountVO.getCustomerNo());
            accountMain.setCustomerName(accountVO.getCustomerName());
            accountMain.setValidstatus("1");
            accountMain.setCreateUser("");
            accountMain.setCreateDate(new Date());
            accountMain.setTotalFee(accountVO.getPayFee());
            accountMain.setTotalUseFee(0.0);
            if (accountVO.getPayType().equals("1")) {
                accountMain.setTotalUseFee(BigDecimal.valueOf(accountMain.getTotalUseFee()).add(BigDecimal.valueOf(accountVO.getPayFee())).doubleValue());
            }
            accountMain.setCurrBalance(accountVO.getPayFee());
        } else {
            accountMain.setUpdateUser("");
            accountMain.setUpdateDate(new Date());
            accountMain.setTotalFee(BigDecimal.valueOf(accountMain.getTotalFee()).add(BigDecimal.valueOf(accountVO.getPayFee())).doubleValue());
            accountMain.setCurrBalance(BigDecimal.valueOf(accountMain.getCurrBalance()).add(BigDecimal.valueOf(accountVO.getPayFee())).doubleValue());
        }
        AccountMain successMain = accountMainDao.save(accountMain);
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setMainId(successMain.getId());
        accountDetail.setPayMethod(accountVO.getPayMethod());
        accountDetail.setPayFee(accountVO.getPayFee());
        accountDetail.setPayDate(new Date());
        accountDetail.setFileName(accountVO.getFileName());
        accountDetail.setRemark(accountVO.getRemark());
        accountDetailDao.save(accountDetail);
    }

    public Page<AccountDetail> getDetailPage(AccountVO accountVO) {
        Pageable pageable = PageRequest.of(accountVO.getPageNum()-1,accountVO.getPageSize());
        return accountDetailDao.getPageByParam(accountVO.getId(),accountVO.getStartDate(),accountVO.getEndDate(),pageable);
    }
}
