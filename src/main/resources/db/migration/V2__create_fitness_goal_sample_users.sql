CREATE TABLE IF NOT EXISTS fitness_goal
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    goal VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO fitness_goal (goal)
VALUES ('Lose weight'),
       ('Build muscle'),
       ('Stay active');

CREATE TABLE IF NOT EXISTS users
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(15)  NOT NULL UNIQUE,
    name            VARCHAR(20),
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(50)  NOT NULL,
    gender          VARCHAR(10),
    date_of_birth   DATE,
    height          DOUBLE,
    weight          DOUBLE,
    fitness_goal_id BIGINT       NOT NULL,
    CONSTRAINT fk_fitness_goal
        FOREIGN KEY (fitness_goal_id)
            REFERENCES fitness_goal (id)
            ON DELETE CASCADE
);

INSERT INTO users (username, name, email, password, gender, date_of_birth, height, weight, fitness_goal_id)
VALUES ('oliver01', 'Oliver', 'oliver@example.com', 'A1@bcDe$', 'Male', '1990-01-15', 175, 70, 1),
       ('amelia02', 'Amelia', 'amelia@example.com', 'B2#deFg$', 'Female', '2008-02-20', 165, 60, 2),
       ('jack03', 'Jack', 'jack@example.com', 'C3$EfGh@', 'Male', '1985-03-25', 180, 75, 3),
       ('olivia04', 'Olivia', 'olivia@example.com', 'D4@GhIj$', 'Female', '2005-04-10', 170, 65, 1),
       ('harry05', 'Harry', 'harry@example.com', 'E5#HiJk$', 'Male', '1975-05-05', 185, 80, 2),
       ('isla06', 'Isla', 'isla@example.com', 'F6$IjKl@', 'Female', '2007-06-12', 160, 55, 3),
       ('charlie07', 'Charlie', 'charlie@example.com', 'G7@JkLm$', 'Male', '1995-07-07', 178, 72, 1),
       ('ava08', 'Ava', 'ava@example.com', 'H8#KlMn$', 'Female', '2009-08-08', 168, 62, 2),
       ('george09', 'George', 'george@example.com', 'I9$LmNo@', 'Male', '1965-09-09', 182, 78, 3),
       ('mia10', 'Mia', 'mia@example.com', 'J1@MnOp$', 'Female', '1980-10-10', 172, 66, 1),
       ('freddie11', 'Freddie', 'freddie@example.com', 'K2#NoPq$', 'Male', '1998-11-11', 177, 73, 2),
       ('grace12', 'Grace', 'grace@example.com', 'L3$OpQr@', 'Female', '2006-12-12', 163, 58, 3),
       ('leo13', 'Leo', 'leo@example.com', 'M4@PqRs$', 'Male', '1970-03-03', 174, 69, 1),
       ('sophie14', 'Sophie', 'sophie@example.com', 'N5#QrSt$', 'Female', '2004-06-06', 169, 64, 2),
       ('archie15', 'Archie', 'archie@example.com', 'O6$RsTu@', 'Male', '1960-09-09', 181, 76, 3);


CREATE TABLE IF NOT EXISTS workout
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id             BIGINT   NOT NULL,
    duration_in_minutes INT      NOT NULL,
    date                DATETIME NOT NULL,
    workout_type_id     BIGINT   NOT NULL,
    CONSTRAINT fk_workout_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_workout_type FOREIGN KEY (workout_type_id) REFERENCES workout_type (id) ON DELETE RESTRICT
);

INSERT INTO workout (user_id, duration_in_minutes, date, workout_type_id)
VALUES (1, 45, '2025-08-01 08:00:00', 1),
       (2, 30, '2025-08-01 09:00:00', 2),
       (3, 60, '2025-08-02 07:30:00', 3),
       (4, 20, '2025-08-02 18:00:00', 4),
       (5, 50, '2025-08-03 06:45:00', 5),
       (6, 40, '2025-08-03 17:00:00', 1),
       (7, 35, '2025-08-04 08:15:00', 2),
       (8, 25, '2025-08-04 19:00:00', 3),
       (9, 55, '2025-08-05 07:00:00', 4),
       (10, 30, '2025-08-05 20:00:00', 5),
       (11, 45, '2025-08-06 06:30:00', 1),
       (12, 60, '2025-08-06 18:30:00', 2),
       (13, 20, '2025-08-07 09:00:00', 3),
       (14, 50, '2025-08-07 17:45:00', 4),
       (15, 35, '2025-08-08 08:00:00', 5),
       (1, 25, '2025-08-08 19:15:00', 1),
       (2, 40, '2025-08-09 07:45:00', 2),
       (3, 30, '2025-08-09 20:30:00', 3),
       (4, 60, '2025-08-10 06:00:00', 4),
       (5, 45, '2025-08-10 18:00:00', 5),
       (6, 20, '2025-08-11 08:30:00', 1),
       (7, 50, '2025-08-11 19:30:00', 2),
       (8, 35, '2025-08-12 07:15:00', 3),
       (9, 25, '2025-08-12 20:15:00', 4),
       (10, 55, '2025-08-13 06:45:00', 5),
       (11, 30, '2025-08-13 18:45:00', 1),
       (12, 45, '2025-08-14 09:00:00', 2),
       (13, 60, '2025-08-14 17:00:00', 3),
       (14, 20, '2025-08-15 08:00:00', 4),
       (15, 50, '2025-08-15 19:00:00', 5),
       (1, 35, '2025-08-16 07:30:00', 1),
       (2, 25, '2025-08-16 20:30:00', 2),
       (3, 55, '2025-08-17 06:15:00', 3),
       (4, 30, '2025-08-17 18:15:00', 4),
       (5, 45, '2025-08-18 09:30:00', 5),
       (6, 60, '2025-08-18 17:30:00', 1),
       (7, 20, '2025-08-19 08:45:00', 2),
       (8, 50, '2025-08-19 19:45:00', 3),
       (9, 35, '2025-08-20 07:00:00', 4),
       (10, 25, '2025-08-20 20:00:00', 5);

