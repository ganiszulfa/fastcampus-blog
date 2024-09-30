ALTER TABLE post ADD COLUMN category_id INT,
ADD FOREIGN KEY fk_post_category(category_id) REFERENCES category(id)
ON DELETE CASCADE;