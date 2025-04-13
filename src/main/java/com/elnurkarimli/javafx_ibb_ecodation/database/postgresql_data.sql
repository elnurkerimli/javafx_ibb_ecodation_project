CREATE DATABASE user_management;

-- Table Create
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(100) NOT NULL UNIQUE
);

-- Insert (tek tırnak kullanılmalı)
INSERT INTO users (username, password, email)
VALUES ('Hamit', '123456', 'hamitmizrak@gmail.com');

-- Select
SELECT * FROM users;

-- Find User (şifrenin kontrolü eksikti, düzeltildi)
SELECT * FROM users
WHERE username = 'Hamit' AND password = '123456';

-- Update
UPDATE users
SET username = 'Hamit44', password = '12345644', email = 'hamitmizrak@gmail.com44'
WHERE id = 1;

-- Delete
DELETE FROM users WHERE id = 1;