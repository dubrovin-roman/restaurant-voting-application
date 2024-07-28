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
       ('Height 5642', 'RUSSIA, KISLOVODSK, KURORTNY BOULEVARD 13A');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Soup', 560.23, 1),
       ('Steak', 1750.30, 1),
       ('Fish', 1200.52, 1),
       ('Salad', 450.55, 1),
       ('Dessert', 780.00, 1);

INSERT INTO DISH (name, price, restaurant_id, date_of_menu)
VALUES ('Soup Old', 490.23, 1, '2024-07-01'),
       ('Steak Old', 1700.30, 1, '2024-07-01'),
       ('Fish Old', 1130.52, 1, '2024-07-01');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Hamburger', 750.23, 3),
       ('Steak', 2752.30, 3),
       ('Salad', 550.55, 3);

INSERT INTO DISH (name, price, restaurant_id, date_of_menu)
VALUES ('Salad Old', 120.52, 2, '2024-07-01');

INSERT INTO VOTE (user_id, restaurant_id)
VALUES (2, 3),
       (3, 2);

INSERT INTO VOTE (date_voting, user_id, restaurant_id)
VALUES ('2024-07-27', 2, 3),
       ('2024-07-26', 2, 1),
       ('2024-07-25', 2, 3),
       ('2024-07-27', 1, 3),
       ('2024-07-26', 1, 1),
       ('2024-07-25', 1, 1);