CREATE TABLE IF NOT EXISTS users (
    id INT auto_increment,
    email VARCHAR(64),
    login VARCHAR(64),
    name VARCHAR(64),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS friends (
    user_from_id INT REFERENCES users(id),
    user_to_id INT REFERENCES users(id),
    PRIMARY KEY (user_from_id, user_to_id)
);

CREATE TABLE IF NOT EXISTS genres (
    id INT auto_increment,
    name VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS mpa (
    id INT auto_increment,
    name VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS films (
    id INT auto_increment,
    name VARCHAR(64),
    description VARCHAR(255),
    release_date DATE,
    duration BIGINT,
    rate INT,
    mpa_id INT REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS films_genres (
    genre_id INT REFERENCES genres(id),
    film_id INT REFERENCES films(id),
    PRIMARY KEY (genre_id, film_id)
);

CREATE TABLE IF NOT EXISTS likes (
    user_id INT REFERENCES users(id),
    film_id INT REFERENCES films(id),
    PRIMARY KEY (user_id, film_id)
);
