-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MERCHANT
   drop constraint UK_ID cascade;
alter table T_MERCHANT
  add constraint PK_OTG_MERCHANT primary key (ID);