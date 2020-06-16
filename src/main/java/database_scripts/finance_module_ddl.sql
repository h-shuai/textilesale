drop table if exists t_finance_link_order;
create table t_finance_link_order
(
    id           bigint auto_increment comment '主键ID',
    order_no     varchar(200)  null comment '订单号',
    batch_no     varchar(200)  null comment '批次号',
    status       char          null comment '状态：0-无效，1-有效',
    create_date  timestamp     null comment '创建日期',
    order_amount double(16, 2) null comment '订单金额',
    constraint t_finance_link_order_id_uindex
        unique (id)
)
    comment '财务与订单关联表';

alter table t_finance_link_order
    add primary key (id);

drop table if exists t_finance_detail_info;
create table t_finance_detail_info
(
    id           bigint auto_increment comment '主键ID',
    batch_no     varchar(200) null comment '批次号',
    paymethod_id bigint       null comment '支付方式编号',
    pay_amount   double(8, 2) null comment '支付金额',
    status       char         null comment '状态：0-无效，1-有效',
    constraint t_finance_detail_info_id_uindex
        unique (id)
)
    comment '财务明细信息';

alter table t_finance_detail_info
    add primary key (id);



drop table if exists t_account_main;
create table t_account_main
(
    id            bigint auto_increment comment '主键ID',
    customer_no   varchar(30)   null comment '客户编码',
    customer_name varchar(200)  null comment '客户名称',
    total_fee     double(14, 2) null comment '总金额',
    total_use_fee double(14, 2) null comment '总使用金额',
    curr_balance  double(14, 2) null comment '当前余额',
    validstatus   char          null comment '状态：0-无效，1-有效',
    create_user   varchar(500)  null comment '创建人',
    create_date   timestamp     null comment '创建时间',
    update_user   varchar(500)  null comment '修改人',
    update_date   timestamp     null comment '修改日期',
    constraint t_account_main_id_uindex
        unique (id)
)
    comment '账户主表信息';

alter table t_account_main
    add primary key (id);

drop table if exists t_account_detail;
create table t_account_detail
(
    id         bigint auto_increment comment '主键ID',
    main_id    bigint        null comment '主表ID',
    pay_method bigint        null comment '支付方式',
    pay_fee    double(14, 2) null comment '支付金额',
    pay_date   timestamp     null comment '支付日期',
    remark     varchar(1000) null comment '备注',
    file_name  varchar(500)  null comment '附件名称',
    constraint t_acount_detail_id_uindex
        unique (id)
)
    comment '账户明细表';

alter table t_account_detail
    add primary key (id);

