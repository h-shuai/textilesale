truncate table t_order_info;
truncate table t_order_detail_info;
truncate table t_order_stock_detail_info;
/*待审核*/
INSERT INTO t_order_info(order_no, customer_name, customer_phone_no, order_date, order_amount, delivery_mode,
                         delivery_address, contact, contact_phone_no, consignment_department, customer_address,
                         order_status, clerk_name, stocker, stock_date, deliverer, deliverer_date, create_user,
                         update_user, create_date, update_date)
SELECT '20202504151603979',
       '张三',
       '13987654321',
       now(),
       10000,
       '1',
       '浙江省杭州市拱墅区',
       '张三',
       '13987654321',
       '1',
       '浙江省杭州市拱墅区',
       '0',
       NULL,
       NULL,
       NULL,
       NULL,
       NULL,
       'admin',
       'admin',
       now(),
       now()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504151603979', '001'),
       '20202504151603979',
       CONCAT('ABC', '-', '001'),
       10,
       400,
       4000,
       null,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504151603979', '002'),
       '20202504151603979',
       CONCAT('ABC', '-', '002'),
       10,
       600,
       6000,
       null,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;


/*待备货*/
INSERT INTO t_order_info(order_no, customer_name, customer_phone_no, order_date, order_amount, delivery_mode,
                         delivery_address, contact, contact_phone_no, consignment_department, customer_address,
                         order_status, clerk_name, stocker, stock_date, deliverer, deliverer_date, create_user,
                         update_user, create_date, update_date)
SELECT '20202504152455384',
       '张三',
       '13987654321',
       now(),
       20000,
       '1',
       '浙江省杭州市拱墅区',
       '张三',
       '13987654321',
       '1',
       '浙江省杭州市拱墅区',
       '1',
       NULL,
       NULL,
       NULL,
       NULL,
       NULL,
       'admin',
       'admin',
       now(),
       now()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504152455384', '001'),
       '20202504152455384',
       CONCAT('DEF', '-', '001'),
       20,
       400,
       8000,
       null,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504152455384', '002'),
       '20202504152455384',
       CONCAT('DEF', '-', '002'),
       20,
       600,
       12000,
       null,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;


/*备货中*/
INSERT INTO t_order_info(order_no, customer_name, customer_phone_no, order_date, order_amount, delivery_mode,
                         delivery_address, contact, contact_phone_no, consignment_department, customer_address,
                         order_status, clerk_name, stocker, stock_date, deliverer, deliverer_date, create_user,
                         update_user, create_date, update_date)
SELECT '20202504152731617',
       '张三',
       '13987654321',
       now(),
       30000,
       '1',
       '浙江省杭州市拱墅区',
       '张三',
       '13987654321',
       '1',
       '浙江省杭州市拱墅区',
       '1',
       NULL,
       NULL,
       NULL,
       NULL,
       NULL,
       'admin',
       'admin',
       now(),
       now()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504152731617', '001'),
       '20202504152731617',
       CONCAT('ASD', '-', '001'),
       10,
       1000,
       10000,
       null,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504152731617', '002'),
       '20202504152731617',
       CONCAT('ASD', '-', '002'),
       20,
       500,
       10000,
       null,
       '1',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504152731617', '003'),
       '20202504152731617',
       CONCAT('ASD', '-', '003'),
       50,
       200,
       10000,
       null,
       '2',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;


INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504152731617', '002', '001'),
       '20202504152731617',
       CONCAT('ASD', '-', '002'),
       1,
       300,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504152731617', '003', '001'),
       '20202504152731617',
       CONCAT('ASD', '-', '003'),
       1,
       100,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;
INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504152731617', '003', '002'),
       '20202504152731617',
       CONCAT('ASD', '-', '003'),
       2,
       100,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;


/*备货完成*/
INSERT INTO t_order_info(order_no, customer_name, customer_phone_no, order_date, order_amount, delivery_mode,
                         delivery_address, contact, contact_phone_no, consignment_department, customer_address,
                         order_status, clerk_name, stocker, stock_date, deliverer, deliverer_date, create_user,
                         update_user, create_date, update_date)
SELECT '20202504153726525',
       '张三',
       '13987654321',
       now(),
       40000,
       '1',
       '浙江省杭州市拱墅区',
       '张三',
       '13987654321',
       '1',
       '浙江省杭州市拱墅区',
       '2',
       NULL,
       NULL,
       NULL,
       NULL,
       NULL,
       'admin',
       'admin',
       now(),
       now()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504153726525', '001'),
       '20202504153726525',
       CONCAT('QWE', '-', '001'),
       20,
       1000,
       20000,
       null,
       '2',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

insert into t_order_detail_info(id, order_no, product_type, unit_price, product_length, amount, extra_crafts,
                                stock_status, create_user, update_user, create_date, update_date)
SELECT CONCAT('20202504153726525', '002'),
       '20202504153726525',
       CONCAT('QWE', '-', '002'),
       40,
       500,
       20000,
       null,
       '2',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;


INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '001', '001'),
       '20202504153726525',
       CONCAT('QWE', '-', '001'),
       1,
       100,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '001', '002'),
       '20202504153726525',
       CONCAT('QWE', '-', '001'),
       2,
       200,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '001', '003'),
       '20202504153726525',
       CONCAT('QWE', '-', '001'),
       3,
       300,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '001', '004'),
       '20202504153726525',
       CONCAT('QWE', '-', '001'),
       4,
       400,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;

INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '002', '001'),
       '20202504153726525',
       CONCAT('QWE', '-', '002'),
       1,
       300,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;
INSERT INTO t_order_stock_detail_info(ID, ORDER_NO, PRODUCT_TYPE, STOCK_NO, STOCK_LENGTH, status, CREATE_USER,
                                      UPDATE_USER,
                                      CREATE_DATE, UPDATE_DATE)
SELECT CONCAT('20202504153726525', '002', '002'),
       '20202504153726525',
       CONCAT('QWE', '-', '002'),
       2,
       200,
       '0',
       'admin',
       'admin',
       NOW(),
       NOW()
FROM DUAL;