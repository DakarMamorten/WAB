create table product_brand
(
    id          bigserial,
    name        varchar(255)   not null,
    description text,
    image_path  varchar(255)   not null,
    constraint pk_product_brand primary key (id)
);
