drop table if exists t_driver_mapper;
drop table if exists t_license_mapper;
drop table if exists t_cosign_mapper;
-- auto-generated definition
create table t_driver_mapper
(
    id           bigint auto_increment comment '主键ID',
    driver_name  varchar(30) null comment '司机名称',
    driver_phone varchar(30) null comment '司机联系电话',
    status       char        null comment '状态：1-有效，0-无效',
    constraint t_driver_mapper_id_uindex
        unique (id)
)
    comment '司机数据字段';

alter table t_driver_mapper
    add primary key (id);

-- auto-generated definition
create table t_license_mapper
(
    id             bigint auto_increment comment '主键ID',
    license_no     varchar(30) null comment '车牌号',
    status         char        null comment '状态：1-有效，0-无效',
    if_restriction char        null comment '是否限行：0-否，1-是',
    constraint t_license_mapper_id_uindex
        unique (id)
)
    comment '车牌数据字典';

alter table t_license_mapper
    add primary key (id);

-- auto-generated definition
create table t_cosign_mapper
(
    id               varchar(200) not null comment '主键ID',
    cosign_name      varchar(200) null comment '指定托运部',
    cosign_address   varchar(500) null comment '托运部地址',
    cosign_phone     varchar(30)  null comment '托运部联系电话',
    status           char         null comment '状态：1-有效，0-无效',
    cosign_link_user varchar(200) null comment '托运部联系人',
    constraint t_cosign_mapper_id_uindex
        unique (id)
)
    comment '指定托运部数据字典';

alter table t_cosign_mapper
    add primary key (id);
