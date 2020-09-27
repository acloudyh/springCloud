
/*
    该sql包含以下
    1. seata建库建表语句
    2. 项目中需要的建表语句
    3. undo_log建表语句
*/

-- ----------------------------
-- seata 建库建表语句
-- ----------------------------
Drop Database if exists seata;
create database seata;
-- the table to store GlobalSession data
drop table if exists seata.global_table;
create table seata.global_table
(
    `xid`                       varchar(128) not null,
    `transaction_id`            bigint,
    `status`                    tinyint      not null,
    `application_id`            varchar(32),
    `transaction_service_group` varchar(32),
    `transaction_name`          varchar(128),
    `timeout`                   int,
    `begin_time`                bigint,
    `application_data`          varchar(2000),
    `gmt_create`                datetime,
    `gmt_modified`              datetime,
    primary key (`xid`),
    key `idx_gmt_modified_status` (`gmt_modified`, `status`),
    key `idx_transaction_id` (`transaction_id`)
);

-- the table to store BranchSession data
drop table if exists seata.branch_table;
create table seata.branch_table
(
    `branch_id`         bigint       not null,
    `xid`               varchar(128) not null,
    `transaction_id`    bigint,
    `resource_group_id` varchar(32),
    `resource_id`       varchar(256),
    `lock_key`          varchar(128),
    `branch_type`       varchar(8),
    `status`            tinyint,
    `client_id`         varchar(64),
    `application_data`  varchar(2000),
    `gmt_create`        datetime,
    `gmt_modified`      datetime,
    primary key (`branch_id`),
    key `idx_xid` (`xid`)
);

-- the table to store lock data
drop table if exists seata.lock_table;
create table seata.lock_table
(
    `row_key`        varchar(128) not null,
    `xid`            varchar(96),
    `transaction_id` long,
    `branch_id`      long,
    `resource_id`    varchar(256),
    `table_name`     varchar(32),
    `pk`             varchar(36),
    `gmt_create`     datetime,
    `gmt_modified`   datetime,
    primary key (`row_key`)
);


-- ----------------------------
-- 项目中所需要的建库建表语句
-- ----------------------------
Drop Database if exists seata_order;
create database seata_order;

Drop Database if exists seata_storage;
create database seata_storage;

Drop Database if exists seata_account;
create database seata_account;

DROP TABLE IF EXISTS seata_order.t_order;
CREATE TABLE seata_order.t_order
(
    `id`         BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`    BIGINT(11)     DEFAULT NULL COMMENT '用户id',
    `product_id` BIGINT(11)     DEFAULT NULL COMMENT '产品id',
    `count`      INT(11)        DEFAULT NULL COMMENT '数量',
    `money`      DECIMAL(11, 0) DEFAULT NULL COMMENT '金额',
    `status`     INT(1)         DEFAULT NULL COMMENT '订单状态：0：创建中; 1：已完结'
) ENGINE = INNODB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS seata_storage.t_storage;
CREATE TABLE seata_storage.t_storage
(
    `id`         BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
    `total`      INT(11)    DEFAULT NULL COMMENT '总库存',
    `used`       INT(11)    DEFAULT NULL COMMENT '已用库存',
    `residue`    INT(11)    DEFAULT NULL COMMENT '剩余库存'
) ENGINE = INNODB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS seata_account.t_account;
CREATE TABLE seata_account.t_account
(
    `id`      BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `user_id` BIGINT(11)     DEFAULT NULL COMMENT '用户id',
    `total`   DECIMAL(10, 0) DEFAULT NULL COMMENT '总额度',
    `used`    DECIMAL(10, 0) DEFAULT NULL COMMENT '已用余额',
    `residue` DECIMAL(10, 0) DEFAULT '0' COMMENT '剩余可用额度'
) ENGINE = INNODB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;


INSERT INTO seata_storage.t_storage(`id`, `product_id`, `total`, `used`, `residue`)
VALUES ('1', '1', '100', '0', '100');

INSERT INTO seata_account.t_account(`id`, `user_id`, `total`, `used`, `residue`)
VALUES ('1', '1', '1000', '0', '1000');



-- the table to store seata xid data
-- 0.7.0+ add context
-- you must to init this sql for you business databese. the seata server not need it.
-- 此脚本必须初始化在你当前的业务数据库中，用于AT 模式XID记录。与server端无关（注：业务数据库）
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
DROP TABLE IF EXISTS seata_order.undo_log;
CREATE TABLE seata_order.undo_log
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS seata_storage.undo_log;
CREATE TABLE seata_storage.undo_log
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS seata_account.undo_log;
CREATE TABLE seata_account.undo_log
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;