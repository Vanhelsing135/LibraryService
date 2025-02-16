create table Book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    author VARCHAR(255) NOT NULL
);
