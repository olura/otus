drop table if exists Author CASCADE;
drop table if exists Genre CASCADE;
drop table if exists Book CASCADE;

create table Author (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

create table Genre (
    id SERIAL PRIMARY KEY,
    title varchar NOT NULL
);

create table Book (
    id SERIAL PRIMARY KEY,
    title varchar NOT NULL,
    author_id int references Author(id) ON DELETE SET NULL,
    genre_id int references Genre(id) ON DELETE SET NULL
);