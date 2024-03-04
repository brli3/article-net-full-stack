-- DROP DATABASE IF EXISTS article_net;
-- CREATE DATABASE article_net;

-- user table
CREATE TABLE users (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(20) NOT NULL UNIQUE,
                        password VARCHAR(32),
                        nickname VARCHAR(10) DEFAULT '',
                        email VARCHAR(128) DEFAULT '',
                        user_pic VARCHAR(128) DEFAULT '',
                        create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- category table
CREATE TABLE categories (
                          id SERIAL PRIMARY KEY,
                          category_name VARCHAR(32) NOT NULL,
                          category_alias VARCHAR(32) NOT NULL,
                          create_user INT NOT NULL,
                          create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_category_user FOREIGN KEY (create_user) REFERENCES users (id)
);

-- article table
CREATE TABLE articles (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(32) NOT NULL,
                         content VARCHAR(10000) NOT NULL,
                         cover_img VARCHAR(128) NOT NULL,
                         state VARCHAR(12) DEFAULT 'draft',
                         category_id INT,
                         create_user INT NOT NULL,
                         create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories (id),
                         CONSTRAINT fk_article_user FOREIGN KEY (create_user) REFERENCES users (id)
);
