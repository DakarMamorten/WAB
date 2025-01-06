create table if not exists address
(
    id                 bigserial    not null,
    first_name         varchar(255) not null,
    last_name          varchar(255) not null,
    email              varchar(255) not null,
    address1           varchar(255) not null,
    address2           varchar(255),
    phone              varchar(20)  not null,
    postal_code        varchar(6)   not null,
    city               varchar(255) not null,
    payment_method_id  bigint       not null,
    shipment_method_id bigint       not null,
    constraint pk_address primary key (id),
    constraint uq_address unique (email),
    constraint fk_address1 foreign key (payment_method_id) references payment_method (id),
    constraint fk_address2 foreign key (shipment_method_id) references shipment_method (id)
);

create index idx_city1 on address (phone);
create index idx_city2 on address (payment_method_id);
create index idx_city3 on address (shipment_method_id);
