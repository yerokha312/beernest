CREATE TABLE styles
(
    style_id serial      NOT NULL,
    name     varchar(50) NOT NULL,
    PRIMARY KEY (style_id)
);

INSERT INTO styles (style_id, name)
VALUES (1, 'ALE'),
       (2, 'LAGER'),
       (3, 'HYBRID');

CREATE TABLE subtype
(
    subtype_id serial      NOT NULL,
    style_id   int         NOT NULL,
    name       varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (subtype_id),
    FOREIGN KEY (style_id)
        REFERENCES styles (style_id)
);

CREATE TABLE brand
(
    brand_id bigserial NOT NULL,
    name     varchar(50) DEFAULT NULL,
    PRIMARY KEY (brand_id)
);

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
    subtype_id      int            DEFAULT NULL,
    brand_id        int            DEFAULT NULL,
    alcohol         numeric(2, 1)  DEFAULT NULL,
    container_id    int            DEFAULT NULL,
    size            int            DEFAULT NULL,
    sell_price      numeric(10, 2) GENERATED ALWAYS AS (purchase_price * price_multi) STORED,
    purchase_price  numeric(10, 2) DEFAULT NULL,
    country         varchar(50)    DEFAULT NULL,
    description     text           DEFAULT NULL,
    price_multi     decimal        DEFAULT 1.5,
    stock_available integer        DEFAULT NULL,
    PRIMARY KEY (beer_id),
    FOREIGN KEY (style_id)
        REFERENCES styles (style_id),
    FOREIGN KEY (subtype_id)
        REFERENCES subtype (subtype_id),
    FOREIGN KEY (brand_id)
        REFERENCES brand (brand_id),
    FOREIGN KEY (container_id)
        REFERENCES container (container_id)
);
