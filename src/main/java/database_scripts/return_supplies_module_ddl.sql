drop table if exists t_reject_record;
drop table if exists t_reject_supplies_info;
CREATE TABLE t_reject_record
(
    record_no         varchar(30) NOT NULL COMMENT '退货记录编号',
    reject_date       datetime     DEFAULT NOW() COMMENT '退货时间',
    supplier_phone_no varchar(15)  DEFAULT NULL COMMENT '供应商电话',
    supplier_type     varchar(1)   DEFAULT '1' COMMENT '供应商类型 0-个人 1-单位',
    supplier_name     varchar(200) COMMENT '供应商名称',
    industry_type     varchar(1) COMMENT '所属行业 1-童装 2-男装 3-女装 4-家纺',
    create_user       varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user       varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date       datetime     DEFAULT NOW() COMMENT '创建时间',
    update_date       datetime     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (record_no)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='退回供应商记录';

CREATE TABLE t_reject_supplies_info
(
    id             bigint       NOT NULL auto_increment COMMENT '主键',
    record_no      varchar(30)  NOT NULL COMMENT '退货记录编号',
    product_type   varchar(10)  NOT NULL COMMENT '产品型号',
    product_length double(8, 2) NOT NULL COMMENT '长度(米)',
    url            varchar(250) DEFAULT NULL COMMENT '样品图像地址',
    create_user    varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user    varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date    datetime     DEFAULT NOW() COMMENT '创建时间',
    update_date    datetime     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='退回供应商物品详细信息';

