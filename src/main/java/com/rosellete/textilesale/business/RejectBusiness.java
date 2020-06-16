package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectRecordVO;
import com.rosellete.textilesale.vo.RejectSuppliesVO;

public interface RejectBusiness {
    PageInfo<RejectRecordVO> getRejectRecordList(RejectRecordVO rejectRecordVO);

    void saveRejectRecord(RejectRecordSaveVO rejectRecordSaveVO);

    RejectRecordSaveVO getRejectRecordDetail(Integer recordNo);

    Integer getSequenceNo();
}
