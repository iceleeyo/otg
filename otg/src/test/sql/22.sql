  -- Add/modify columns 
alter table T_OTG_TRANSACTION add reference_txn_number VARCHAR2(20);
-- Add comments to the columns 
comment on column T_OTG_TRANSACTION.reference_txn_number
  is '退款交易中的原交易流水号';
