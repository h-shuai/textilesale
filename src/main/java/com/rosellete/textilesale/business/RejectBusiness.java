package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.RejectRecordVO;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectSuppliesInfoVO;

public interface RejectBusiness {
    PageInfo<RejectRecordVO> getRejectRecordList(RejectRecordVO rejectRecordVO);

    void saveRejectRecord(RejectRecordSaveVO rejectRecordSaveVO);

    PageInfo<RejectSuppliesInfoVO> getRejectRecordDetail(String recordNo);
}
