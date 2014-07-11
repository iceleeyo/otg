-- Add/modify columns 
alter table T_OTG_TRANSACTION add transaction_type VARCHAR2(1) default 0;
-- Add comments to the columns 
comment on column T_OTG_TRANSACTION.transaction_type
  is '交易类别，（0=新交易， 1=撤销交易）';