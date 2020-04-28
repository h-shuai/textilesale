package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RejectBusiness;
import com.rosellete.textilesale.interfaces.RejectApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RejectRecordVO;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectSuppliesInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reject")
public class RejectController implements RejectApi {
    @Autowired
    private RejectBusiness rejectBusiness;

    @Override
    public RestResponse getRejectRecordList(@RequestBody RejectRecordVO rejectRecordVO) {
        PageInfo<RejectRecordVO> pageInfo = rejectBusiness.getRejectRecordList(rejectRecordVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse saveRejectRecord(@RequestBody RejectRecordSaveVO rejectRecordSaveVO) {
        RestResponse response = new RestResponse();
        try {
            rejectBusiness.saveRejectRecord(rejectRecordSaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg("系统内部错误，请稍后重试");
            log.error("保存退货记录数据{}失败", rejectRecordSaveVO, e);
        }
        return response;
    }

    @Override
    public RestResponse getRejectRecordDetail(String recordNo) {
        PageInfo<RejectSuppliesInfoVO> pageInfo = rejectBusiness.getRejectRecordDetail(recordNo);
        return new RestResponse(pageInfo);
    }
}
