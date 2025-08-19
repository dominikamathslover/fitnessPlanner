CREATE TABLE workout_type
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO workout_type (name)
VALUES ('Cardio'),
       ('Strength Training'),
       ('Stretching'),
       ('Cycling'),
       ('Swimming'),
       ('Running');