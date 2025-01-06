create table shipment_method
(
    id   bigint       not null,
    code varchar(255) not null,
    name varchar(255) not null,
    constraint pk_shipment_method primary key (id),
    constraint uq_shipment_method1 unique (code),
    constraint uq_shipment_method2 unique (name)
);
