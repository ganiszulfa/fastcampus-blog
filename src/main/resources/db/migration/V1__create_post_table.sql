CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body text NOT NULL,
    slug VARCHAR(100) NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    updated_at BIGINT,
    created_at BIGINT NOT NULL,
    published_at BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;