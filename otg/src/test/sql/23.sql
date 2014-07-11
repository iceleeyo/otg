-- Add/modify columns 
alter table T_OTG_TRANSACTION add card_info VARCHAR2(100);
-- Add comments to the columns 
comment on column T_OTG_TRANSACTION.card_info
  is '交易的缩略银行卡信息';

  
  -- Add/modify columns 
alter table T_OTG_NOTIFICATION add card_info VARCHAR2(100);
-- Add comments to the columns 
comment on column T_OTG_NOTIFICATION.card_info
  is '交易的缩略银行卡信息';
