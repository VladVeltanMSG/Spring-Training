CREATE TABLE IF NOT EXISTS customer (
    id UUID NOT NULL PRIMARY KEY ,
    first_name varchar(20),
    last_name varchar(20),
    username varchar(20),
    password varchar(20),
    email_address varchar(30)
);