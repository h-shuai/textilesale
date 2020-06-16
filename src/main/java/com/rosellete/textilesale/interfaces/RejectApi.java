package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RejectRecordSaveVO;
import com.rosellete.textilesale.vo.RejectRecordVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface RejectApi {
    @PostMapping(value = "/queryRejectRecordList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getRejectRecordList(@RequestBody @Valid RejectRecordVO rejectRecordVO);

    @PostMapping(value = "/saveRejectRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveRejectRecord(@RequestBody @Valid RejectRecordSaveVO rejectRecordSaveVO);

    @GetMapping(path = "/viewRejectRecord")
    RestResponse getRejectRecordDetail(@RequestParam("recordNo") Integer recordNo);
}
