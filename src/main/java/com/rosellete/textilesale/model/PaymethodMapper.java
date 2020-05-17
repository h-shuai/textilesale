package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 支付方式数据字典(TPaymethodMapper)实体类
 *
 * @author makejava
 * @since 2020-05-15 16:15:51
 */
@Entity
@Table(name = "t_paymethod_mapper")
@Data
public class PaymethodMapper implements Serializable {
    private static final long serialVersionUID = -85897508578809915L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 支付方式
    */
    @Column(name = "method_name")
    private String methodName;
    /**
    * 账号
    */
    @Column(name = "account")
    private String account;
    /**
    * 状态：0-无效，1-有效
    */
    @Column(name = "status")
    private Integer status;
}
