package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 账户主表信息(TAccountMain)实体类
 *
 * @author makejava
 * @since 2020-06-10 16:14:12
 */
@Entity
@Table(name = "t_account_main")
@Data
public class AccountMain extends Page implements Serializable {
    private static final long serialVersionUID = 258695447099800613L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 客户编码
    */
    @Column(name = "customer_no")
    private String customerNo;
    /**
    * 客户名称
    */
    @Column(name = "customer_name")
    private String customerName;
    /**
    * 总金额
    */
    @Column(name = "total_fee")
    private Double totalFee;
    /**
    * 总使用金额
    */
    @Column(name = "total_use_fee")
    private Double totalUseFee;
    /**
    * 当前余额
    */
    @Column(name = "curr_balance")
    private Double currBalance;
    /**
    * 状态：0-无效，1-有效
    */
    @Column(name = "validstatus")
    private String validstatus;
    /**
    * 创建人
    */
    @Column(name = "create_user")
    private String createUser;
    /**
    * 创建时间
    */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /**
    * 修改人
    */
    @Column(name = "update_user")
    private String updateUser;
    /**
    * 修改日期
    */
    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}
