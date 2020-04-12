package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user_info")
@Data
public class UserInfo extends Page {
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;//主键id

    @Column(name = "depart_id")
    private String departId;//部门字符串

    @Column(name = "password")
    private String password;//密码

    @Column(name = "account")
    private String account;//登录名

    @Column(name = "sex")
    private  int sex;//性别

    @Column(name = "position")
    private String position;//职位

    @Column(name = "name")
    private String name;//显示名称

    @Column(name = "phone")
    private String phone;//手机号码

    @Column(name = "line_phone")
    private  String linePhone;//座机

    private String card;//身份证

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd") //入参
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;//出生日期

    @Column(name = "address")
    private String address;//地址

    @Column(name = "email")
    private String email;//邮箱

    @Column(name = "status")
    private Integer status;//有效状态

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;//创建时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;//更新时间

    @Column(name = "create_user")
    private String createUser;//创建人

    @Column(name = "update_user")
    private String updateUser;//更新人

    @Column(name = "description")
    private String description;//描述

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", departId='" + departId + '\'' +
                ", password='" + password + '\'' +
                ", account='" + account + '\'' +
                ", sex=" + sex +
                ", position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", linePhone='" + linePhone + '\'' +
                ", card='" + card + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
