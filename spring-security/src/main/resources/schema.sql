drop table if exists Author CASCADE;
drop table if exists Genre CASCADE;
drop table if exists Book CASCADE;
drop table if exists Comment CASCADE;
drop table if exists Users CASCADE;
drop table if exists Authorities CASCADE;

create table Author (
    id bigserial PRIMARY KEY,
    name varchar NOT NULL unique
);

create table Genre (
    id bigserial PRIMARY KEY,
    title varchar NOT NULL unique
);

create table Book (
    id bigserial PRIMARY KEY,
    title varchar NOT NULL,
    author_id bigint references Author(id),
    genre_id bigint references Genre(id)
);

create table Comment (
    id bigserial PRIMARY KEY,
    text varchar(255),
    book_id bigint references Book(id) ON DELETE CASCADE
);

create table Users (
	username varchar not null primary key,
	password varchar not null,
	email varchar not null
);
