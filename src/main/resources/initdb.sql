CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(31) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS task (
    id SERIAL PRIMARY KEY,
    content VARCHAR(63),
    date TIMESTAMP WITH TIME ZONE,
    is_complete BOOLEAN,
    author VARCHAR(31) NOT NULL
);

ALTER TABLE task ALTER COLUMN date SET DEFAULT current_timestamp;
ALTER TABLE task ALTER COLUMN is_complete SET DEFAULT false;
ALTER TABLE task ADD FOREIGN KEY (author) REFERENCES app_user(username);