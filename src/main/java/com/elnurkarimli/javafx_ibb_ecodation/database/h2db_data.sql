CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(100),
                       password VARCHAR(100),
                       email VARCHAR(255)
);


INSERT INTO users (id, username, password, email)
VALUES (1, 'elnur', '123456', 'elnur@gmail.com')
ON CONFLICT (id)
    DO UPDATE SET username = EXCLUDED.username, password = EXCLUDED.password, email = EXCLUDED.email;

-- Select
SELECT * FROM users;

-- Find User (username ve password kontrolü)
SELECT * FROM users WHERE username = 'Hamit' AND password = '123456';

-- Update
UPDATE users
SET username = 'elnur12', password = '12345644', email = 'elnur1@gmail.com44'
WHERE id = 1;

-- Delete
DELETE FROM users WHERE id = 1;