CREATE TABLE IF NOT EXISTS sousers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sousername VARCHAR(32) NOT NULL,
    sopassword VARCHAR(32) NOT NULL,
    score int
);

CREATE TABLE IF NOT EXISTS questions (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    CONSTRAINT Fk_users_questions FOREIGN KEY (userId) REFERENCES sousers(id),
    title VARCHAR(32) NOT NULL,
    text VARCHAR(32) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    score INT NOT NULL
);

CREATE TABLE IF NOT EXISTS answers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    questionId INT,
    CONSTRAINT Fk_questions_answers FOREIGN KEY (questionId) REFERENCES questions(id),
    userId INT,
    CONSTRAINT Fk_users_answers FOREIGN KEY (userId) REFERENCES sousers(id),
    text VARCHAR(32) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    score INT NOT NULL
);

CREATE TABLE IF NOT EXISTS answerVotes (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    answerId INT,
    CONSTRAINT Fk_answerVotes_answers FOREIGN KEY (answerId) REFERENCES answers(id),
    userId INT,
    CONSTRAINT Fk_answerVotes_sousers FOREIGN KEY (userId) REFERENCES sousers(id),
    voteType tinyint
);



CREATE TABLE IF NOT EXISTS questionVotes (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    questionId INT,
    CONSTRAINT Fk_questionVotes_questions FOREIGN KEY (questionId) REFERENCES questions(id),
    userId INT,
    CONSTRAINT Fk_questionVotes_users FOREIGN KEY (userId) REFERENCES sousers(id),
    voteType tinyint
);


CREATE TABLE IF NOT EXISTS tags (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tagName VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS questionsTags (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    questionId INT,
    CONSTRAINT Fk_questionsTags_questions FOREIGN KEY (questionId) REFERENCES questions(id),
    tagId INT,
    CONSTRAINT Fk_questionsTags_tags FOREIGN KEY (tagId) REFERENCES tags(id)
);



