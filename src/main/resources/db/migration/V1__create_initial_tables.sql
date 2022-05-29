CREATE TABLE users (
    id uuid NOT NULL PRIMARY KEY,
    username varchar(256) NOT NULL,
    avatar_url varchar(2045) NOT NULL,
    birth_date DATE NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE INDEX users_username_idx on users(username);

CREATE TABLE game_scores (
    id BIGINT NOT NULL PRIMARY KEY auto_increment,
--    user_id uuid NOT NULL,
    score integer NOT NULL,
    penalty integer NOT NULL,
    average_answer_time integer NOT NULL,
    game_difficulty VARCHAR(6) NOT NULL,
    created_at DATETIME NOT NULL
--    FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE INDEX game_scores_idx on game_scores(score, penalty, average_answer_time , game_difficulty);