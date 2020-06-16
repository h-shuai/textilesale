drop table if exists t_storage_record;
drop table if exists t_storage_package_info;
drop table if exists t_package_inventory_info;
CREATE TABLE t_storage_record
(
    record_no          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '入库记录编号',
    storage_date       DATETIME     DEFAULT NOW() COMMENT '入库时间',
    storage_type       VARCHAR(1)   DEFAULT '1' COMMENT '入库类型 1-供应商发货 2-客户退货',
    consignor_no       INT      NOT NULL COMMENT '供应商编号/客户编号',
    package_quantity   SMALLINT NOT NULL COMMENT '包裹数量',
    storage_clerk_name VARCHAR(50)  DEFAULT NULL COMMENT '抄包员',
    create_user        VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user        VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date        DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date        DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (record_no)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8 COMMENT ='入库信息';

CREATE TABLE t_storage_package_info
(
    id                  BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    package_no          VARCHAR(40)   NOT NULL COMMENT '包裹编号',
    record_no           BIGINT        NOT NULL COMMENT '入库记录编号',
    package_status      VARCHAR(1) COMMENT '包裹状态 0-待拆包 1-已拆包',
    packed_stock_length DOUBLE(10, 2) NOT NULL COMMENT '长度(米)',
    create_user         VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user         VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date         DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date         DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='入库包裹信息';

CREATE TABLE t_package_inventory_info
(
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    package_no   VARCHAR(40)  NOT NULL COMMENT '包裹编号',
    stock_no     INT          NOT NULL COMMENT '物品编号',
    product_type VARCHAR(10)  DEFAULT NULL COMMENT '产品型号',
    image_name   VARCHAR(200) DEFAULT NULL COMMENT '样品图名称',
    stock_length DOUBLE(8, 2) NOT NULL COMMENT '长度(米)',
    create_user  VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user  VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date  DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date  DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='入库物品信息';