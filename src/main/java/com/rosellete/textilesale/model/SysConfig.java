package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_sys_config")
@Data
public class SysConfig implements Serializable {

    @Id
    @Column(name = "id", nullable = false,unique = true,length = 10)
    private Integer id;

    @Column(name = "code_name", length = 100)
    private String codeName;

    @Column(name = "code_value", length = 100)
    private String codeValue;

    @Column(name = "memo", length = 100)
    private String memo;

    @Column(name = "create_user", length = 100)
    private String createUser;

    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

}
