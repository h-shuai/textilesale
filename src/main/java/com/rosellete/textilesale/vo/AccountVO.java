package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.AccountMain;
import lombok.Data;

import java.util.Date;

@Data
public class AccountVO extends AccountMain {
    private Long payMethod;

    private Double payFee;

    private String fileName;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
