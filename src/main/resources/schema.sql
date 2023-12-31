CREATE TABLE styles
(
    style_id serial      NOT NULL,
    name     varchar(50) NOT NULL,
    PRIMARY KEY (style_id)
);

INSERT INTO styles (style_id, name)
VALUES (1, 'ALE'),
       (2, 'LAGER');

CREATE TABLE container
(
    container_id serial      NOT NULL,
    type         varchar(50) NOT NULL,
    PRIMARY KEY (container_id)
);

INSERT INTO container (container_id, type)
VALUES (1, 'CAN'),
       (2, 'BOTTLE');

CREATE TABLE beer
(
    beer_id         bigserial NOT NULL,
    name            varchar(50)    DEFAULT NULL,
    style_id        int            DEFAULT NULL,
    alcohol         numeric(2, 1)  DEFAULT NULL,
    container_id    int            DEFAULT NULL,
    size            int            DEFAULT NULL,
    sell_price      numeric(10, 2) GENERATED ALWAYS AS (purchase_price * price_multi) STORED,
    purchase_price  numeric(10, 2) DEFAULT NULL,
    country         varchar(50)    DEFAULT NULL,
    price_multi     decimal        DEFAULT 1.5,
    stock_available integer        DEFAULT NULL,
    PRIMARY KEY (beer_id),
    FOREIGN KEY (style_id)
        REFERENCES styles (style_id),
    FOREIGN KEY (container_id)
        REFERENCES container (container_id)
);

CREATE TABLE customer
(
    customer_id  bigserial NOT NULL,
    first_name   varchar(50) DEFAULT NULL,
    last_name    varchar(50) DEFAULT NULL,
    dob          date        DEFAULT NULL,
    email        varchar(50) DEFAULT NULL,
    phone_number varchar(50) DEFAULT NULL,
    password     varchar(50) DEFAULT NULL,
    PRIMARY KEY (customer_id)
);
