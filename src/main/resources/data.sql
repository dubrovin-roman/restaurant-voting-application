INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, address)
VALUES ('Astoria', 'RUSSIA, MOSCOW, PETROVKA 38'),
       ('Pancakes', 'RUSSIA, MOSCOW, TKACHEVKA 451'),
       ('Pelmeni No. 1', 'RUSSIA, MOSCOW, KUTUZOVA 228')