CREATE TABLE authors
(
    id SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE author_details
(
    id     SERIAL PRIMARY KEY,
    author_id     INT UNIQUE,
    bio           TEXT,
    date_of_birth DATE,
    nationality   VARCHAR(50),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE publishers
(
    id SERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(255)
);

CREATE TABLE books
(
    id          SERIAL PRIMARY KEY,
    title            VARCHAR(200) NOT NULL,
    author_id        INT,
    publisher_id     INT,
    publication_year INT,
    FOREIGN KEY (author_id) REFERENCES authors (id),
    FOREIGN KEY (publisher_id) REFERENCES publishers (id)
);

CREATE TABLE categories
(
    category_id SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL
);

CREATE TABLE books_and_categories
(
    book_id     INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);
