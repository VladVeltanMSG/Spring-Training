CREATE TABLE IF NOT EXISTS orderr (
    id UUID NOT NULL PRIMARY KEY ,
    customer UUID REFERENCES customer(id),
    created_at timestamp,
    address_country varchar(50),
    address_city varchar(50),
    address_county varchar(50),
    address_street varchar(50)
);