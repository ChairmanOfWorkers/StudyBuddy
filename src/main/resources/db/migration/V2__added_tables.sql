CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       registration_date TIMESTAMP NOT NULL
);

CREATE TABLE profiles (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL UNIQUE REFERENCES users(id),
                          full_name VARCHAR(255),
                          bio TEXT
);

CREATE TABLE subjects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_subjects (
                               id SERIAL PRIMARY KEY,
                               user_id INT NOT NULL REFERENCES users(id),
                               subject_id INT NOT NULL REFERENCES subjects(id)
);

CREATE TYPE request_status AS ENUM ('PENDING','ACCEPTED','DECLINED');

CREATE TABLE study_requests (
                                id SERIAL PRIMARY KEY,
                                sender_id INT NOT NULL REFERENCES users(id),
                                receiver_id INT NOT NULL REFERENCES users(id),
                                subject_id INT NOT NULL REFERENCES subjects(id),
                                status request_status NOT NULL,
                                request_date TIMESTAMP NOT NULL
);
