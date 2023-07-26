CREATE TABLE if not exists stock (
    product UUID REFERENCES product(id),
    location UUID REFERENCES location(id),
    quantity integer,
    primary key(product,location)
);