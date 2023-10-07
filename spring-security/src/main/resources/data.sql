insert into Author (name) values ('Pushkin');
insert into Author (name) values ('Turgenev');
insert into Author (name) values ('Dostoevskiy');
insert into Author (name) values ('Gogol');

insert into Genre (title) values ('Romance');
insert into Genre (title) values ('Drama');
insert into Genre (title) values ('Fairy Tale');

insert into book(title, author_id, genre_id) values ('Evgeniy Onegin', 1, 1);
insert into book(title, author_id, genre_id) values ('Prestuplenie i nakazanie', 3, 2);

insert into Comment(text, book_id) values ('first comment', 1);
insert into Comment(text, book_id) values ('second comment', 1);

insert into Users(username, password, email) values ('admin', '$2a$10$7OpFR2kzKhxvwe37Pi2KL.maJP8wfv8PO/.7sn56Cs8dvvkRwzFgW', 'admin@email.ru');
insert into Users(username, password, email) values ('user', '$2a$10$2Pb36deyHJDMmdCRwzL9C.gbKo2s68SNER7JYttg5JgQ9VzjHQkOO', 'user@email.ru');

