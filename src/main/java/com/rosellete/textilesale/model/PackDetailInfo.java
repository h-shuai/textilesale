package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 打包明细信息表(TPackDetailInfo)实体类
 *
 * @author makejava
 * @since 2020-05-04 14:01:17
 */
@Entity
@Table(name = "t_pack_detail_info")
@Data
public class PackDetailInfo implements Serializable {
    private static final long serialVersionUID = -94151090055149142L;
    /**
    * 主键ID
    */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    /**
    * 订单号
    */
    @Column(name = "order_no")
    private Integer orderNo;
    /**
    * 打包ID
    */
    @Column(name = "pack_id")
    private String packId;
    /**
    * 产品型号
    */
    @Column(name = "product_type")
    private String productType;
    /**
    * 长度
    */
    @Column(name = "stock_length")
    private Double stockLength;
    /**
    * 创建人
    */
    @Column(name = "create_user")
    private String createUser;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    /**
    * 修改人
    */
    @Column(name = "update_user")
    private String updateUser;
    /**
    * 修改时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;
    /**
    * 状态
    */
    @Column(name = "status")
    private Integer status;
    /**
     * 产品图片
     */
    @Column(name = "prod_pic")
    private String prodPic;
    /**
     * 产品图片
     */
    @Column(name = "stock_detail_id")
    private String stockDetailId;
}
