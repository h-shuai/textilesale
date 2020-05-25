package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 打包信息表(TPackInfo)实体类
 *
 * @author makejava
 * @since 2020-05-04 13:59:59
 */
@Entity
@Table(name = "t_pack_info")
@Data
public class PackInfo extends Page implements Serializable {
    private static final long serialVersionUID = -20884321245660816L;
    /**
    * 主键ID
    */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    /**
    * 客户名称
    */
    @Column(name = "customer_name")
    private String customerName;
    /**
     * 客户编号
     */
    @Column(name = "customer_id")
    private String customerId;
    /**
    * 包号
    */
    @Column(name = "pack_no")
    private Integer packNo;
    /**
    * 型号数量
    */
    @Column(name = "product_count")
    private Integer productCount;
    /**
    * 布匹数量
    */
    @Column(name = "piece_count")
    private Integer pieceCount;
    /**
    * 布匹长度
    */
    @Column(name = "rice_count")
    private Double riceCount;
    /**
    * 状态：0-待确认，1-确认通过，2-已发货
    */
    @Column(name = "status")
    private Integer status;
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
     * 打包图片
     */
    @Column(name = "pack_pic")
    private String packPic;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 业务对象类型：0-客户，1-供应商
     */
    @Column(name = "business_type")
    private String businessType;
}
