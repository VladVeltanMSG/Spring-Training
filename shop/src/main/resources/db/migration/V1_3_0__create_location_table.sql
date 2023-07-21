CREATE TABLE if not exists location (
    id UUID NOT NULL PRIMARY KEY ,
    name VARCHAR(20),
    country VARCHAR(40),
    city VARCHAR(40),
    county VARCHAR(40),
    street_address VARCHAR(40)
)