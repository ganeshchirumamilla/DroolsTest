select id,customer_type,cust_year,disc from disc_rules;

Insert into disc_rules values(1,'BUSINESS',2,20);

update DISC_RULES set disc = 25 where id = 1;

update DISC_RULES set CUSTOMER_TYPE = 'CustomerType.BUSINESS' where CUSTOMER_TYPE = 'BUSINESS';

commit;

desc disc_rules;


update disc_rules c set c.cust_year = 3 where id =1;

commit;

