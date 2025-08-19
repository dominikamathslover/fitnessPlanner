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

