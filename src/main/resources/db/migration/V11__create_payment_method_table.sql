create table payment_method
(
    id   bigint       not null,
    code varchar(255) not null,
    name varchar(255) not null,
    constraint pk_payment_method primary key (id),
    constraint uq_payment_method1 unique (code),
    constraint uq_payment_method2 unique (name)
);
