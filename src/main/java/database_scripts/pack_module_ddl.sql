drop table if exists t_pack_info;
drop table if exists t_pack_detail_info;
drop table if exists t_delivery_info;
-- auto-generated definition
-- auto-generated definition
create table t_pack_info
(
    id            varchar(200)                        not null comment '主键ID',
    customer_name varchar(200)                        null comment '客户名称',
    pack_no       int                                 null comment '包号',
    product_count int                                 null comment '型号数量',
    piece_count   int                                 null comment '布匹数量',
    rice_count    int                                 null comment '布匹长度',
    status        char                                null comment '状态：0-待确认，1-确认通过，2-已发货',
    create_user   varchar(30)                         null comment '创建人',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_user   varchar(30)                         null comment '修改人',
    update_time   timestamp default CURRENT_TIMESTAMP null comment '修改时间',
    pack_pic      varchar(255)                        null comment '打包图片',
    customer_id   varchar(200)                        null comment '客户编号',
    constraint t_pack_info_id_uindex
        unique (id)
)
    comment '打包信息表';

alter table t_pack_info
    add primary key (id);

create table t_pack_detail_info
(
    id              varchar(200)                        not null comment '主键ID',
    order_no        varchar(200)                        null comment '订单号',
    pack_id         varchar(200)                        null comment '打包ID',
    product_type    varchar(200)                        null comment '产品型号',
    stock_length    double(8, 2)                        null comment '长度',
    create_user     varchar(30)                         null comment '创建人',
    create_time     timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_user     varchar(30)                         null comment '修改人',
    update_time     timestamp default CURRENT_TIMESTAMP null comment '修改时间',
    status          char                                null comment '状态',
    prod_pic        varchar(255)                        null comment '产品图片',
    stock_detail_id varchar(30)                         null comment '订单明细表ID',
    constraint t_pack_detail_info_id_uindex
        unique (id)
)
    comment '打包明细信息表';

alter table t_pack_detail_info
    add primary key (id);

-- auto-generated definition
create table t_delivery_info
(
    id               varchar(200)                        not null comment '主键ID',
    pack_id          varchar(200)                        null comment '打包ID',
    customer_name    varchar(200)                        null comment '客户名称',
    customer_id      varchar(200)                        null comment '客户ID',
    driver_id        varchar(200)                        null comment '司机ID',
    license_id       varchar(200)                        null comment '车牌号ID',
    type             char                                null comment '类型：1-司机发货，2-上门自提',
    create_time      timestamp default CURRENT_TIMESTAMP null comment '发车时间',
    status           char                                null comment '状态',
    today_depart_num int                                 null comment '当日发车次数',
    constraint t_delivery_info_id_uindex
        unique (id)
)
    comment '发货信息';

alter table t_delivery_info
    add primary key (id);
