CREATE SCHEMA IF NOT EXISTS bugzapperj;

SET SCHEMA bugzapperj;

CREATE TABLE IF NOT EXISTS bugzapperj.bug
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    description TEXT,
    status VARCHAR
(
    255
) NOT NULL,
    priority VARCHAR
(
    255
) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );