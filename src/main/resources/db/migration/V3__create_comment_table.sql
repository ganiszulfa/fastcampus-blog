CREATE TABLE comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    body text NOT NULL,
    created_at BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;