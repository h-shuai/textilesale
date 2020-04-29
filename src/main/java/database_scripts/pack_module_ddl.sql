drop table if exists t_pack_info;
drop table if exists t_pack_detail_info;
-- auto-generated definition
create table t_pack_info
(
    id            varchar(30)                         not null comment '主键ID',
    order_no      varchar(200)                        null comment '订单号',
    pack_no       int                                 null comment '包号',
    product_count int                                 null comment '型号数量',
    piece_count   int                                 null comment '布匹数量',
    rice_count    int                                 null comment '布匹长度',
    status        char                                null comment '状态：0-待确认，1-确认通过，2-确认不通过',
    create_user   varchar(30)                         null comment '创建人',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_user   varchar(30)                         null comment '修改人',
    update_time   timestamp default CURRENT_TIMESTAMP null comment '修改时间',
    constraint t_pack_info_id_uindex
        unique (id)
)
    comment '打包信息表';

alter table t_pack_info
    add primary key (id);

-- auto-generated definition
create table t_pack_detail_info
(
    id           varchar(30)                         not null comment '主键ID',
    order_no     varchar(200)                        null comment '订单号',
    pack_id      varchar(30)                         null comment '打包ID',
    product_type varchar(200)                        null comment '产品型号',
    stock_length double(8,2)                         null comment '长度',
    create_user  varchar(30)                         null comment '创建人',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_user  varchar(30)                         null comment '修改人',
    update_time  timestamp default CURRENT_TIMESTAMP null comment '修改时间',
    status       char                                null comment '状态',
    constraint t_pack_detail_info_id_uindex
        unique (id)
)
    comment '打包明细信息表';

alter table t_pack_detail_info
    add primary key (id);


