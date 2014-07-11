-- Add/modify columns 
alter table T_OTG_TRANSACTION add UPDATE_TIME date;
-- Add comments to the columns 
comment on column T_OTG_TRANSACTION.UPDATE_TIME
  is '核对状态时间';