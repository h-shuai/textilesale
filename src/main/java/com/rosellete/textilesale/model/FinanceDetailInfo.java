package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 财务明细信息(TFinanceDetailInfo)实体类
 *
 * @author makejava
 * @since 2020-05-17 18:21:57
 */
@Entity
@Table(name = "t_finance_detail_info")
@Data
public class FinanceDetailInfo implements Serializable {
    private static final long serialVersionUID = 643598581883969472L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 批次号
    */
    @Column(name = "batch_no")
    private String batchNo;
    /**
    * 支付方式编号
    */
    @Column(name = "paymethod_id")
    private Long paymethodId;
    /**
    * 支付金额
    */
    @Column(name = "pay_amount")
    private Double payAmount;
    /**
    * 状态：0-无效，1-有效
    */
    @Column(name = "status")
    private String status;
}
