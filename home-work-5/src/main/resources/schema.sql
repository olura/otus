drop table if exists Author CASCADE;
drop table if exists Genre CASCADE;
drop table if exists Book CASCADE;

create table Author (
    id BIGSERIAL PRIMARY KEY,
    name varchar NOT NULL unique
);

create table Genre (
    id BIGSERIAL PRIMARY KEY,
    title varchar NOT NULL unique
);

create table Book (
    id BIGSERIAL PRIMARY KEY,
    title varchar NOT NULL,
    author_id int references Author(id) ON DELETE SET NULL,
    genre_id int references Genre(id) ON DELETE SET NULL
);