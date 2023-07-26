CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY,
    name VARCHAR(20),
    description varchar(150),
    price DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    category UUID REFERENCES product_category(id),
    supplier varchar(150),
    image_url VARCHAR(255)
);
