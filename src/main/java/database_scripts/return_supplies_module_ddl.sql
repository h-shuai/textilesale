drop table if exists t_reject_record;
drop table if exists t_reject_supplies_info;
drop table if exists t_reject_supplies_stock_detail;
CREATE TABLE t_reject_record
(
    record_no          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '退货记录编号',
    reject_date        DATETIME     DEFAULT NOW() COMMENT '退货时间',
    supplier_no        INT          NOT NULL COMMENT '供应商编号',
    product_type_count INT          NOT NULL COMMENT '匹数',
    product_length_sum DOUBLE(8, 2) NOT NULL COMMENT '长度(米)',
    create_user        VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user        VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date        DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date        DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (record_no)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8 COMMENT ='退回供应商记录';

CREATE TABLE t_reject_supplies_info
(
    id             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_no      BIGINT       NOT NULL COMMENT '退货记录编号',
    product_type   VARCHAR(10)  NOT NULL COMMENT '产品型号',
    image_name     VARCHAR(200) DEFAULT NULL COMMENT '样品图名称',
    product_length DOUBLE(8, 2) NOT NULL COMMENT '长度(米)',
    create_user    VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user    VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date    DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date    DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='退回供应商物品详细信息';

CREATE TABLE t_reject_supplies_stock_detail
(
    id           BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_no    BIGINT       NOT NULL COMMENT '订单编号',
    product_type VARCHAR(10)  NOT NULL COMMENT '产品型号',
    stock_no     SMALLINT(6)  NOT NULL COMMENT '货物序号',
    stock_length DOUBLE(8, 2) NOT NULL COMMENT '退货数量(米)',
    status       CHAR(1)      DEFAULT '0' COMMENT '打包状态：1-是，0-否',
    create_user  VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user  VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date  DATETIME     DEFAULT NULL COMMENT '创建时间',
    update_date  DATETIME     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='退回供应商详情表';