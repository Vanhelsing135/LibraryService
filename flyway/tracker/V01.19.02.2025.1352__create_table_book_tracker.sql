CREATE TABLE BookTracker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bookId INT NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    takenAt DATETIME,
    returnBy DATETIME
);
