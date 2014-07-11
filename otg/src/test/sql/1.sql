-- Create table
create table  T_OTG_TRANSACTION
(
  AMOUNT                  NUMBER(10,2) default 0.00 not null,
  ID                      NUMBER not null,
  TRANSACTION_NO          VARCHAR2(20) not null,
  STATE                   VARCHAR2(1) not null,
  WHEN_REQUESTED          DATE not null,
  WHEN_RESPONSED          DATE,
  WHEN_CONCLUDED          DATE,
  ORDER_ID                NUMBER not null,
  APPLICATION             VARCHAR2(1) not null,
  VERSION                 NUMBER default 1 not null,
  MERCHANT_CODE           VARCHAR2(200) not null,
  CHANNEL_ID              VARCHAR2(2) not null,
  ORDER_NO                VARCHAR2(100),
  CONTACT_NAME            VARCHAR2(50),
  EXTERNAL_TRANSACTION_NO VARCHAR2(50),
  CONTACT_MOBILE          VARCHAR2(20),
  SYNCHRONIZING_METHOD    VARCHAR2(2),
  MESSAGE                 VARCHAR2(200),
  CHARGE_FOR              VARCHAR2(2),
  GATE_WAY                VARCHAR2(10),
  CURRENCY                VARCHAR2(5),
  NOTIFICATION_SEQ        NUMBER,
  PARTNER_ID              NUMBER
);
-- Add comments to the table 
comment on table  T_OTG_TRANSACTION
  is '在线交易,交易聚合根';
-- Add comments to the columns 
comment on column  T_OTG_TRANSACTION.AMOUNT
  is '交易金额';
comment on column  T_OTG_TRANSACTION.ID
  is '交易主键，唯一确定一笔交易，不在应用中使用';
comment on column  T_OTG_TRANSACTION.TRANSACTION_NO
  is '交易流水号，唯一确定一笔交易';
comment on column  T_OTG_TRANSACTION.STATE
  is '交易状态（0=请求中，1=已响应扣款成功，2=已响应扣款失败，3=已完成）';
comment on column  T_OTG_TRANSACTION.WHEN_REQUESTED
  is '交易请求时间';
comment on column  T_OTG_TRANSACTION.WHEN_RESPONSED
  is '交易响应时间';
comment on column  T_OTG_TRANSACTION.WHEN_CONCLUDED
  is '交易完成时间';
comment on column  T_OTG_TRANSACTION.ORDER_ID
  is '付款应用的订单标识';
comment on column  T_OTG_TRANSACTION.APPLICATION
  is '付款应用的标识';
comment on column  T_OTG_TRANSACTION.VERSION
  is '乐观锁版本号，从1开始';
comment on column  T_OTG_TRANSACTION.MERCHANT_CODE
  is '商户代码';
comment on column  T_OTG_TRANSACTION.CHANNEL_ID
  is '支付渠道标识';
comment on column  T_OTG_TRANSACTION.ORDER_NO
  is '付款应用的订单代码';
comment on column  T_OTG_TRANSACTION.CONTACT_NAME
  is '客户姓名（已作废）';
comment on column  T_OTG_TRANSACTION.EXTERNAL_TRANSACTION_NO
  is '外部交易流水号（已作废）';
comment on column  T_OTG_TRANSACTION.CONTACT_MOBILE
  is '客户联系电话（已作废）';
comment on column  T_OTG_TRANSACTION.SYNCHRONIZING_METHOD
  is '完成方式(自动:0,手动:1)（已作废）';
comment on column  T_OTG_TRANSACTION.MESSAGE
  is '支付平台传递来的消息（已作废）';
comment on column  T_OTG_TRANSACTION.CHARGE_FOR
  is '支付用途（已作废）';
comment on column  T_OTG_TRANSACTION.GATE_WAY
  is '网关官方代码';
comment on column  T_OTG_TRANSACTION.CURRENCY
  is '交易币种';
comment on column  T_OTG_TRANSACTION.NOTIFICATION_SEQ
  is '处理的通知序列号';
comment on column  T_OTG_TRANSACTION.PARTNER_ID
  is '对接伙伴的标识';
-- Create/Recreate primary, unique and foreign key constraints 
alter table  T_OTG_TRANSACTION
  add constraint PK_OTG_TRANSACTION primary key (ID);
alter table  T_OTG_TRANSACTION
  add constraint UK_OTG_TRANSACTION_1 unique (TRANSACTION_NO);
-- Create/Recreate indexes 
create index  IDX_OTG_TRANSACTION_1 on  T_OTG_TRANSACTION (WHEN_REQUESTED);
create index  IDX_OTG_TRANSACTION_2 on  T_OTG_TRANSACTION (APPLICATION, ORDER_ID);


-- Create table
create table  T_OTG_CHANNEL
(
  VERSION              NUMBER default 1 not null,
  ID                   VARCHAR2(2) not null,
  NAME                 VARCHAR2(50),
  AVAILABLE_CURRENCIES VARCHAR2(100) default 'CNY' not null
);
-- Add comments to the columns 
comment on column  T_OTG_CHANNEL.VERSION
  is '乐观锁版本号，从1开始';
comment on column  T_OTG_CHANNEL.ID
  is '标识，唯一确定一个支付渠道';
comment on column  T_OTG_CHANNEL.NAME
  is '名称';
comment on column  T_OTG_CHANNEL.AVAILABLE_CURRENCIES
  is '可用币种，以","分隔';
-- Create/Recreate primary, unique and foreign key constraints 
alter table  T_OTG_CHANNEL
  add constraint PK_OTG_CHANNEL primary key (ID);
  
-- Create table
create table  T_MERCHANT
(
  ID                 NUMBER(8) not null,
  MERCHANT_NAME      VARCHAR2(200) not null,
  MERCHANT_CODE      VARCHAR2(200) not null,
  MERCHANT_BRANCH_ID NUMBER(8) not null,
  MERCHANT_CHANNEL   NUMBER(8) not null,
  MERCHANT_HOLDER    VARCHAR2(200),
  MERCHANT_KEY       VARCHAR2(2000) not null,
  ENABLED            NUMBER(1) not null,
  LATEST_MODIFY_DATE DATE,
  LATEST_MODIFY_USER NUMBER,
  MERCHANT_TYPE      NUMBER(2),
  EMAIL              VARCHAR2(50),
  VERSION            NUMBER(8) default 1 not null
);
-- Add comments to the columns 
comment on column  T_MERCHANT.MERCHANT_NAME
  is '商户名';
comment on column  T_MERCHANT.MERCHANT_CODE
  is '商户号';
comment on column  T_MERCHANT.MERCHANT_BRANCH_ID
  is '商户所属分社ID';
comment on column  T_MERCHANT.MERCHANT_CHANNEL
  is '商户所属渠道ID(1银联 2春秋商旅卡 3非网付 4付费通 5财付通 6建设银行 7支付宝 10招行语音11建行语音)';
comment on column  T_MERCHANT.MERCHANT_HOLDER
  is '商户所有者';
comment on column  T_MERCHANT.MERCHANT_KEY
  is '商户密钥';
comment on column  T_MERCHANT.ENABLED
  is '是否可用，1可用，0不可用';
comment on column  T_MERCHANT.LATEST_MODIFY_DATE
  is '最后修改时间';
comment on column  T_MERCHANT.LATEST_MODIFY_USER
  is '最后修改人ID';
comment on column  T_MERCHANT.MERCHANT_TYPE
  is '商户所属业务';
comment on column  T_MERCHANT.EMAIL
  is 'email';
-- Create/Recreate primary, unique and foreign key constraints 
alter table  T_MERCHANT
  add constraint UK_ID unique (ID);
alter table  T_MERCHANT
  add constraint UK_OTG_MERCHANT_1 unique (MERCHANT_BRANCH_ID, MERCHANT_CHANNEL);

create table  T_OTG_PARTNER
(
  ID                 NUMBER not null,
  VERSION            NUMBER default 1 not null,
  NAME               VARCHAR2(100) not null,
  AVAILABLE_CHANNELS VARCHAR2(500)
);
-- Add comments to the columns 
comment on column  T_OTG_PARTNER.ID
  is '标识，唯一确定一个对接伙伴';
comment on column  T_OTG_PARTNER.VERSION
  is '乐观锁版本号，从1开始';
comment on column  T_OTG_PARTNER.NAME
  is '名称';
comment on column  T_OTG_PARTNER.AVAILABLE_CHANNELS
  is '可用支付渠道的标识，以","分隔';
-- Create/Recreate primary, unique and foreign key constraints 
alter table  T_OTG_PARTNER
  add constraint PK_OTG_PARTNER primary key (ID);
alter table  T_OTG_PARTNER
  add constraint UK_OTG_PARTNER_NAME unique (NAME);

-- Create sequence 
create sequence SEQ_OTG_TRANSACTION
minvalue 1
maxvalue 9999999
start with 5983
increment by 1
cache 20;

-- Create table
create table T_OTG_GATEWAY
(
  CHANNEL_ID VARCHAR2(2) not null,
  CODE       VARCHAR2(10) not null,
  DIALECT    VARCHAR2(10),
  PRIORITY   NUMBER default 0
);
-- Add comments to the columns 
comment on column T_OTG_GATEWAY.CHANNEL_ID
  is '支付渠道标识';
comment on column T_OTG_GATEWAY.CODE
  is '网关官方代码';
comment on column T_OTG_GATEWAY.DIALECT
  is '网关在支付渠道的方言代码（支付渠道对网关的定义）';
comment on column T_OTG_GATEWAY.PRIORITY
  is '使用优先值，从0开始，数字越小，优先级越高';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OTG_GATEWAY
  add constraint PK_OTG_GATEWAY primary key (CHANNEL_ID, CODE);

-- Create table
create table T_OTG_PARTNER_RECOMMENDED
(
  PARTNER_ID NUMBER not null,
  CHANNEL    VARCHAR2(2) not null,
  GATEWAY    VARCHAR2(10) not null,
  PRIORITY   NUMBER not null
);
-- Add comments to the columns 
comment on column T_OTG_PARTNER_RECOMMENDED.PARTNER_ID
  is '对接伙伴标识';
comment on column T_OTG_PARTNER_RECOMMENDED.CHANNEL
  is '支付渠道标识';
comment on column T_OTG_PARTNER_RECOMMENDED.GATEWAY
  is '网关官方代码';
comment on column T_OTG_PARTNER_RECOMMENDED.PRIORITY
  is '使用优先值，从0开始，数字越小，优先级越高';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OTG_PARTNER_RECOMMENDED
  add constraint PK_OTG_PARTNER_RECOMMENDED primary key (PARTNER_ID, CHANNEL, GATEWAY);  

-- Create sequence 
create sequence SEQ_OTG_MERCHANT
minvalue 1
maxvalue 99999999
start with 10740
increment by 1
cache 20;

-- Create sequence 
create sequence SEQ_OTG_NOTIFICATION
minvalue 1
maxvalue 99999999
start with 17021
increment by 1
cache 20;

-- Create table
create table T_OTG_NOTIFICATION
(
  SEQUENCE      NUMBER not null,
  TXN_NO        VARCHAR2(20) not null,
  EXT_TXN_NO    VARCHAR2(50),
  AMOUNT        NUMBER(10,2) not null,
  CHARGED       VARCHAR2(1) not null,
  MESSAGE       VARCHAR2(200),
  SIGNATURE     VARCHAR2(4000),
  CURRENCY      VARCHAR2(5) not null,
  SYNC_METHOD   VARCHAR2(2) not null,
  WHEN_RECEIVED DATE not null
);
-- Add comments to the columns 
comment on column T_OTG_NOTIFICATION.SEQUENCE
  is '标识，唯一确定一次通知';
comment on column T_OTG_NOTIFICATION.TXN_NO
  is '交易流水号，同t_otg_transaction.transaction_no';
comment on column T_OTG_NOTIFICATION.EXT_TXN_NO
  is '外部交易流水号，支付渠道唯一确定一笔交易的标识';
comment on column T_OTG_NOTIFICATION.AMOUNT
  is '交易金额';
comment on column T_OTG_NOTIFICATION.CHARGED
  is '是否扣款成功（0=失败，1=成功）';
comment on column T_OTG_NOTIFICATION.MESSAGE
  is '返回消息';
comment on column T_OTG_NOTIFICATION.SIGNATURE
  is '签名';
comment on column T_OTG_NOTIFICATION.CURRENCY
  is '交易币种';
comment on column T_OTG_NOTIFICATION.SYNC_METHOD
  is '同步方式（A=同步，S=异步，M=手工）';
comment on column T_OTG_NOTIFICATION.WHEN_RECEIVED
  is '接收时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OTG_NOTIFICATION
  add constraint PK_OTG_NOTIFICATION primary key (SEQUENCE);
-- Create/Recreate indexes 
create index IDX_OTG_NOTIFICATION_1 on T_OTG_NOTIFICATION (TXN_NO);

-- Create sequence 
create sequence SEQ_PAY_SERIAL_NUMBER
minvalue 0
maxvalue 999999
start with 4340
increment by 1
cache 20
cycle;