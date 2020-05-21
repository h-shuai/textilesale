package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 发货信息(TDeliveryInfo)实体类
 *
 * @author makejava
 * @since 2020-05-12 16:13:27
 */
@Entity
@Table(name = "t_delivery_info")
@Data
public class DeliveryInfo implements Serializable {
    private static final long serialVersionUID = -96453968641791224L;
    /**
    * 主键ID
    */
    @Id
    @Column(name = "id", unique = true,nullable = false)
    private String id;
    /**
    * 打包ID
    */
    @Column(name = "pack_id")
    private String packId;
    /**
    * 客户名称
    */
    @Column(name = "customer_name")
    private String customerName;
    /**
    * 客户ID
    */
    @Column(name = "customer_id")
    private String customerId;
    /**
    * 司机ID
    */
    @Column(name = "driver_id")
    private String driverId;
    /**
    * 车牌号ID
    */
    @Column(name = "license_id")
    private String licenseId;
    /**
    * 类型：1-司机发货，2-上门自提
    */
    @Column(name = "type")
    private Integer type;
    /**
    * 发车时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //入参
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    /**
    * 状态
    */
    @Column(name = "status")
    private Integer status;
    /**
    * 当日发车次数
    */
    @Column(name = "today_depart_num")
    private Integer todayDepartNum;
    /**
     * 接收单文件名称
     */
    @Column(name = "receive_file_url")
    private String recerveFileUrl;
}
