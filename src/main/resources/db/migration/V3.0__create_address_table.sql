create table if not exists address
(
    id          bigserial    not null,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    street_name varchar(255) not null,
    house       varchar(50)  not null,
    flat        varchar(50),
    postal_code varchar(6)   not null,
    city        varchar(255) not null,
    phone       varchar(20)  not null,
    email       varchar(255) not null,
    constraint pk_address primary key (id),
    constraint uq_address unique (email)
);

create index idx_city on address (phone);
