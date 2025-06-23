CREATE TABLE orders
(
    id            SERIAL PRIMARY KEY,
    customer_name VARCHAR(100)   NOT NULL,
    order_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price  NUMERIC(10, 2) NOT NULL CHECK (total_price >= 0)
);

CREATE TABLE items
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100)   NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE order_items
(
    order_id INT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    item_id  INT NOT NULL REFERENCES items (id),
    quantity INT NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (order_id, item_id)
);

CREATE TABLE deliveries
(
    id                      SERIAL PRIMARY KEY,
    order_id                INT UNIQUE  NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    address                 TEXT        NOT NULL,
    delivery_status         VARCHAR(50) NOT NULL DEFAULT 'pending',
    estimated_delivery_date DATE
);
