create table employees
(
   id integer not null,
   first_name varchar(255) not null, 
   last_name varchar(255) not null,
   email_address varchar(255) not null,
   primary key(id)
);

create table address
(
   addressId integer not null,
   address varchar(255) not null,
   id integer not null,
   primary key(addressId)
   
);