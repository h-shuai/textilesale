drop table if exists t_sys_config;
CREATE TABLE t_sys_config
(
    id         smallint  NOT NULL auto_increment  COMMENT '编号',
    code_name       varchar(30)     DEFAULT NULL COMMENT '配置代码',
    code_value varchar(30)  DEFAULT NULL COMMENT '配置',
    memo   varchar(200)  DEFAULT NULL COMMENT '说明',
    create_user       varchar(100) DEFAULT NULL COMMENT '创建人',
    create_date       datetime     DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT=10000
  DEFAULT CHARSET = utf8 comment ='系统配置';
