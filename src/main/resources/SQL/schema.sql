SET FOREIGN_KEY_CHECKS=0;

drop table if exists users;
drop table if exists books;
drop table if exists records;
DROP TABLE if EXISTS seckill;
DROP TABLE if EXISTS success_killed;

CREATE TABLE users (
    username VARCHAR(25) NOT NULL,
    email VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(25) NOT NULL,
    userType INTEGER NOT NULL
);

CREATE TABLE books (
    ISBN  VARCHAR(100) PRIMARY KEY NOT NULL,
    bookName VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    publishedDate TIMESTAMP,
    bookType  INT,
    codeInLib VARCHAR(100),
    locationInLib VARCHAR(100),
    description VARCHAR(100),
    price DOUBLE,
    downloadTimes INTEGER ,
    viewTimes INTEGER ,
    prefacepath VARCHAR (400),
    pdffilepath VARCHAR (400)
);

CREATE TABLE records(
    id INT PRIMARY KEY auto_increment,
    date   TIMESTAMP NOT NULL,
    book_id VARCHAR (100) NOT NULL,
    user_id VARCHAR (50) NOT  NULL,
    recordType VARCHAR (100) NOT NULL ,
    FOREIGN KEY (book_id) REFERENCES books(ISBN),
    FOREIGN KEY (user_id) REFERENCES users(email)
);



-- 创建库存明细表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR (120) NOT NULL  COMMENT '商品名称',
`number` INT NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL  COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO seckill(name,number,start_time,end_time)
VALUES
 ('1000元秒杀iphone7',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
 ('50元秒杀ipad air',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
 ('200元秒杀mac book',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
 ('100元秒杀红米note',100,'2016-10-02 00:00:00','2016-10-03 00:00:00');

--  秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态表示-1:无效 0:成功 1:已付款 2:已发货',
`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*联合主键，防止用户重复秒杀*/
KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
# 连接数据库控制台



