-- Add/modify columns 
alter table T_OTG_TRANSACTION add checking_state varchar2(1) default 0;
-- Add comments to the columns 
comment on column T_OTG_TRANSACTION.checking_state
  is '核对状态，0=Unchecked，1=Valid，2=Invalid，3=Dead';