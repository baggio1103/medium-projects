CREATE SCHEMA  if NOT exists public;

CREATE TABLE posts
(
    id         SERIAL PRIMARY KEY,
    title      TEXT NOT NULL,
    content    TEXT NOT NULL,
    author     TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO posts (title, content, author)
VALUES ('Getting Started with SQL', 'This post is about the basics of SQL.', 'John Doe'),
       ('Understanding JOINs in SQL', 'Learn how to use different types of JOINs in SQL.', 'Jane Smith'),
       ('Advanced SQL Queries', 'Dive into more advanced SQL queries.', 'John Doe'),
       ('SQL Best Practices', 'Tips and tricks for writing efficient SQL.', 'Alice Johnson'),
       ('Working with Transactions in SQL', 'Learn how to manage transactions in SQL.', 'Bob Brown'),
       ('Introduction to SQL Indexing', 'An overview of indexing in SQL.', 'Jane Smith'),
       ('How to Optimize SQL Queries', 'Techniques for optimizing SQL queries.', 'Alice Johnson'),
       ('Common SQL Mistakes', 'Avoid these common mistakes when writing SQL.', 'John Doe'),
       ('Understanding SQL Constraints', 'Learn about various constraints in SQL.', 'Bob Brown'),
       ('Using Subqueries in SQL', 'How to use subqueries effectively in SQL.', 'Jane Smith');
