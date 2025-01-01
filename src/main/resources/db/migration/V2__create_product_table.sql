create table product
(
    id          bigserial,
    name        varchar(255)   not null,
    description text,
    price       decimal(10, 2) not null,
    brand       varchar(100)   not null,
    image_path  varchar(255)   not null,
    constraint pk_product primary key (id)
);
