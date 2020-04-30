drop table if exists t_order_info;
drop table if exists t_order_detail_info;
drop table if exists t_order_stock_detail_info;
CREATE TABLE t_order_info
(
    order_no               varchar(30) NOT NULL COMMENT '订单编号',
    customer_name          varchar(200) COMMENT '客户名称',
    customer_phone_no      varchar(15) COMMENT '客户电话',
    order_date             datetime COMMENT '下单时间',
    order_amount           double(16, 2) COMMENT '订单金额',
    delivery_mode          varchar(1)   DEFAULT NULL COMMENT '发货途径',
    delivery_address       varchar(200) COMMENT '收货地址',
    contact                varchar(50) COMMENT '联系人',
    contact_phone_no       varchar(15) COMMENT '联系方式',
    consignment_department varchar(1)   DEFAULT NULL COMMENT '托运部',
    customer_address       varchar(200) DEFAULT NULL COMMENT '托运部',
    order_status           varchar(1)   DEFAULT '0' COMMENT '订单状态 0-订单暂存 1-待备货(提交备货) 2-备货完成 3-备货复核完成 4-重新备货 5-打包完成 6-完成发货',
    clerk_name             varchar(50)  DEFAULT NULL COMMENT '业务员名称',
    stocker                varchar(50)  DEFAULT NULL COMMENT '备货员',
    stock_date             datetime     DEFAULT NULL COMMENT '备货时间',
    deliverer              varchar(50)  DEFAULT NULL COMMENT '发货员',
    deliverer_date         datetime     DEFAULT NULL COMMENT '发货时间',
    create_user            varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user            varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date            datetime     DEFAULT NOW() COMMENT '创建时间',
    update_date            datetime     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (order_no)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='订单表';

CREATE TABLE t_order_detail_info
(
    id             bigint       NOT NULL auto_increment COMMENT '主键',
    order_no       varchar(30)  NOT NULL COMMENT '订单编号',
    product_type   varchar(10)  NOT NULL COMMENT '产品型号',
    unit_price     double       NOT NULL COMMENT '单价(元/米)',
    product_length double(8, 2) NOT NULL COMMENT '预订数量(米)',
    url            varchar(250) DEFAULT NULL COMMENT '样品图像地址',
    amount         double(16, 2) COMMENT '金额',
    extra_crafts   varchar(200) DEFAULT NULL COMMENT '额外工艺',
    stock_status   varchar(1)   DEFAULT '0' COMMENT '备货状态 备货状态 0-待备货 1-备货中 2-备货完成',
    create_user    varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user    varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date    datetime     DEFAULT NOW() COMMENT '创建时间',
    update_date    datetime     DEFAULT NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='订单明细表';

CREATE TABLE t_order_stock_detail_info
(
    id           bigint                   NOT NULL auto_increment COMMENT '主键',
    order_no     varchar(30)              NOT NULL COMMENT '订单编号',
    product_type varchar(10)              NOT NULL COMMENT '产品型号',
    stock_no     smallint                 NOT NULL COMMENT '货物序号',
    stock_length double(8, 2)             NOT NULL COMMENT '备货数量(米)',
    status       char         default '0' null comment '打包状态：1-是，0-否',
    create_user  varchar(100) DEFAULT NULL COMMENT '创建人',
    update_user  varchar(100) DEFAULT NULL COMMENT '修改人',
    create_date  datetime COMMENT '创建时间',
    update_date  datetime COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='订单备货详情表';
