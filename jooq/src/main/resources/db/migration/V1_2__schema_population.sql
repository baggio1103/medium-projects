-- Authors
INSERT INTO authors (name)
VALUES ('J.K. Rowling'),
       ('George Orwell'),
       ('J.R.R. Tolkien');

-- Author Details (One-to-One relation with Authors)
INSERT INTO author_details (author_id, bio, date_of_birth, nationality)
VALUES (1, 'British author, best known for the Harry Potter series', '1965-07-31', 'British'),
       (2, 'English novelist and essayist, known for 1984 and Animal Farm', '1903-06-25', 'British'),
       (3, 'English writer, poet, philologist, best known for The Lord of the Rings', '1892-01-03', 'British');

-- Publishers
INSERT INTO publishers (name, address)
VALUES ('Bloomsbury Publishing', '50 Bedford Square, London'),
       ('Penguin Books', '80 Strand, London'),
       ('HarperCollins', '195 Broadway, New York');

-- Books (One-to-Many relation with Publishers)
INSERT INTO books (title, author_id, publisher_id, publication_year)
VALUES ('Harry Potter and the Philosopher''s Stone', 1, 1, 1997),
       ('1984', 2, 2, 1949),
       ('The Lord of the Rings', 3, 3, 1954);

-- Categories
INSERT INTO categories (name)
VALUES ('Fantasy'),
       ('Dystopian'),
       ('Adventure');

-- BookCategories (Many-to-Many relation between Books and Categories)
INSERT INTO books_and_categories (book_id, category_id)
VALUES (1, 1), -- Harry Potter - Fantasy
       (2, 2), -- 1984 - Dystopian
       (3, 1), -- The Lord of the Rings - Fantasy
       (3, 3); -- The Lord of the Rings - Adventure
