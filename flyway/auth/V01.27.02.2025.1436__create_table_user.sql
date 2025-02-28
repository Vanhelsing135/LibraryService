create table User(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);