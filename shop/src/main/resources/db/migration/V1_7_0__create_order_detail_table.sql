CREATE TABLE if not exists order_detail (
    orderr UUID REFERENCES orderr(id),
    product UUID REFERENCES product(id),
    shipped_from UUID REFERENCES location(id),
    quantity integer,
    PRIMARY KEY (orderr,product)
);