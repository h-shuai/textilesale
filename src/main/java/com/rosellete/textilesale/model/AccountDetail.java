package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 账户明细表(TAcountDetail)实体类
 *
 * @author makejava
 * @since 2020-06-10 16:14:14
 */
@Entity
@Table(name = "t_account_detail")
@Data
public class AccountDetail implements Serializable {
    private static final long serialVersionUID = -96848988481005476L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 主表ID
    */
    @Column(name = "main_id")
    private Long mainId;
    /**
    * 支付方式
    */
    @Column(name = "pay_method")
    private Long payMethod;
    /**
    * 支付金额
    */
    @Column(name = "pay_fee")
    private Double payFee;
    /**
    * 支付日期
    */
    @Column(name = "pay_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;
    /**
    * 备注
    */
    @Column(name = "remark")
    private String remark;
    /**
    * 附件名称
    */
    @Column(name = "file_name")
    private String fileName;
}
