package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 财务与订单关联表(TFinanceLinkOrder)实体类
 *
 * @author makejava
 * @since 2020-05-17 18:22:01
 */
@Entity
@Table(name = "t_finance_link_order")
@Data
public class FinanceLinkOrder extends Page implements Serializable {
    private static final long serialVersionUID = 160481768598209536L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 订单号
    */
    @Column(name = "order_no")
    private String orderNo;
    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private Double orderAmount;
    /**
    * 批次号
    */
    @Column(name = "batch_no")
    private String batchNo;
    /**
    * 状态：0-无效，1-有效
    */
    @Column(name = "status")
    private String status;
    /**
    * 创建日期
    */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
