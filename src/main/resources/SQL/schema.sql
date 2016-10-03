SET FOREIGN_KEY_CHECKS=0;

drop table if exists users;
drop table if exists books;
drop table if exists records;

CREATE TABLE users (
    username VARCHAR(25) NOT NULL,
    email VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(25) NOT NULL,
    userType INTEGER
);

CREATE TABLE books (
    ISBN  VARCHAR(100) PRIMARY KEY NOT NULL,
    bookName VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    publishedDate TIMESTAMP,
    bookType  INT,
    codeInLib VARCHAR(100),
    locationInLib VARCHAR(100),
    description VARCHAR(100),
    price DOUBLE,
    downloadTimes INTEGER ,
    viewTimes INTEGER ,
    prefacepath VARCHAR (400),
    pdffilepath VARCHAR (400)
);

CREATE TABLE records(
    id INT PRIMARY KEY auto_increment,
    date   TIMESTAMP NOT NULL,
    book_id VARCHAR (100) NOT NULL,
    user_id VARCHAR (50) NOT  NULL,
    recordType VARCHAR (100) NOT NULL ,
    FOREIGN KEY (book_id) REFERENCES books(ISBN),
    FOREIGN KEY (user_id) REFERENCES users(email)
);