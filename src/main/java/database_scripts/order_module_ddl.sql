drop table if exists t_order_info;
drop table if exists t_order_detail_info;
drop table if exists t_order_stock_detail_info;
CREATE TABLE t_order_info
(
    order_no               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '订单编号',
    customer_no            INT           NOT NULL COMMENT '客户编号',
    order_date             DATETIME COMMENT '下单时间',
    order_amount           DOUBLE(16, 2) COMMENT '订单金额',
    product_type_count     SMALLINT      NOT NULL DEFAULT 1 COMMENT '型号个数',
    product_length_sum     DOUBLE(16, 2) NOT NULL DEFAULT 0.0 COMMENT '预订长度',
    delivery_mode          VARCHAR(1)             DEFAULT NULL COMMENT '发货途径',
    delivery_address       VARCHAR(200) COMMENT '收货地址',
    liaison                VARCHAR(50) COMMENT '联系人',
    contact                VARCHAR(15) COMMENT '联系方式',
    consignment_department VARCHAR(1)             DEFAULT NULL COMMENT '托运部',
    order_status           VARCHAR(1)             DEFAULT '0' COMMENT '订单状态 0-订单暂存 1-待备货(提交备货) 2-备货完成 3-备货复核完成 4-重新备货 5-打包完成 6-完成发货',
    settle_status          VARCHAR(1)             DEFAULT '0' COMMENT '是否已结清 0-否，1-是',
    clerk_name             VARCHAR(50)            DEFAULT NULL COMMENT '业务员名称',
    stocker                VARCHAR(50)            DEFAULT NULL COMMENT '备货员',
    stock_date             DATETIME               DEFAULT NULL COMMENT '备货时间',
    deliverer              VARCHAR(50)            DEFAULT NULL COMMENT '发货员',
    deliverer_date         DATETIME               DEFAULT NULL COMMENT '发货时间',
    create_user            VARCHAR(100)           DEFAULT NULL COMMENT '创建人',
    update_user            VARCHAR(100)           DEFAULT NULL COMMENT '修改人',
    create_date            DATETIME               DEFAULT NOW() COMMENT '创建时间',
    update_date            DATETIME               DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (order_no)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8 COMMENT ='订单表';

CREATE TABLE t_order_detail_info
(
    id             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    order_no       VARCHAR(30)  NOT NULL COMMENT '订单编号',
    product_type   VARCHAR(10)  NOT NULL COMMENT '产品型号',
    unit_price     DOUBLE       NOT NULL COMMENT '单价(元/米)',
    product_length DOUBLE(8, 2) NOT NULL COMMENT '预订数量(米)',
    image_name     VARCHAR(200) DEFAULT NULL COMMENT '样品图名称',
    amount         DOUBLE(16, 2) COMMENT '金额',
    extra_crafts   VARCHAR(200) DEFAULT NULL COMMENT '额外工艺',
    stock_status   VARCHAR(1)   DEFAULT '0' COMMENT '备货状态 备货状态 0-待备货 1-备货中 2-备货完成',
    create_user    VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user    VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date    DATETIME     DEFAULT NOW() COMMENT '创建时间',
    update_date    DATETIME     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='订单明细表';

CREATE TABLE t_order_stock_detail_info
(
    id           BIGINT                   NOT NULL AUTO_INCREMENT COMMENT '主键',
    order_no     VARCHAR(30)              NOT NULL COMMENT '订单编号',
    product_type VARCHAR(10)              NOT NULL COMMENT '产品型号',
    stock_no     SMALLINT                 NOT NULL COMMENT '货物序号',
    stock_length DOUBLE(8, 2)             NOT NULL COMMENT '备货数量(米)',
    status       CHAR         DEFAULT '0' NULL COMMENT '打包状态：1-是，0-否',
    create_user  VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    update_user  VARCHAR(100) DEFAULT NULL COMMENT '修改人',
    create_date  DATETIME COMMENT '创建时间',
    update_date  DATETIME COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8 COMMENT ='订单备货详情表';
