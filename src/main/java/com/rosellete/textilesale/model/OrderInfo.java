package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单表实体类
 */
@Entity
@Table(name = "t_order_info")
@Data
public class OrderInfo extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_no", nullable = false, length = 30)
    private String orderNo;

    @Column(name = "customer_name", length = 200)
    private String customerName;

    @Column(name = "customer_phone_no", length = 15)
    private String customerPhoneNo;

    @Column(name = "order_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "order_amount", scale = 16, precision = 2)
    private Double orderAmount;

    @Column(name = "delivery_mode", length = 1)
    private String deliveryMode;

    @Column(name = "delivery_address", length = 200)
    private String deliveryAddress;

    @Column(name = "contact", length = 50)
    private String contact;

    @Column(name = "contact_phone_no", length = 15)
    private String contactPhoneNo;

    @Column(name = "consignment_department", length = 1)
    private String consignmentDepartment;

    @Column(name = "customer_address", length = 200)
    private String customerAddress;

    @Column(name = "order_status", length = 1)
    private String orderStatus;

    @Column(name = "clerk_name", length = 50)
    private String clerkName;

    @Column(name = "stocker", length = 50)
    private String stocker;

    @Column(name = "stock_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockDate;

    @Column(name = "deliverer", length = 50)
    private String deliverer;

    @Column(name = "deliverer_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delivererDate;

    @Column(name = "create_user", length = 100)
    private String createUser;

    @Column(name = "update_user", length = 100)
    private String updateUser;

    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}
