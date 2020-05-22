drop table if exists t_reject_record;
drop table if exists t_reject_supplies_info;
drop table if exists t_reject_supplies_stock_detail;
CREATE TABLE t_reject_record
(
    record_no         varchar(30) NOT NULL COMMENT '退货记录编号',
    reject_date       datetime     DEFAULT NOW() COMMENT '退货时间',
    supplier_no int  NOT NULL COMMENT '供应商编号',
    product_type_count   int  NOT NULL COMMENT '匹数',
    product_length_sum double(8, 2) NOT NULL COMMENT '长度(米)',
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
    image_name varchar(200)  DEFAULT NULL COMMENT '样品图名称',
    product_length double(8, 2) NOT NULL COMMENT '长度(米)',
    create_user    varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user    varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date    datetime     DEFAULT NOW() COMMENT '创建时间',
    update_date    datetime     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT=10000
  DEFAULT CHARSET = utf8 comment ='退回供应商物品详细信息';

CREATE TABLE t_reject_supplies_stock_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  record_no varchar(30) NOT NULL COMMENT '订单编号',
  product_type varchar(10) NOT NULL COMMENT '产品型号',
  stock_no smallint(6) NOT NULL COMMENT '货物序号',
  stock_length double(8,2) NOT NULL COMMENT '退货数量(米)',
  status char(1) DEFAULT '0' COMMENT '打包状态：1-是，0-否',
  create_user varchar(100) DEFAULT NULL COMMENT '创建人',
  update_user varchar(100) DEFAULT NULL COMMENT '修改人',
  create_date datetime DEFAULT NULL COMMENT '创建时间',
  update_date datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='退回供应商详情表';