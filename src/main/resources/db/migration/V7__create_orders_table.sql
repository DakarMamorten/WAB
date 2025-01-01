create table if not exists orders
(
    id             uuid           not null,
    user_email     varchar(255),
    order_date     timestamp      not null default now(),
    total_price    numeric(10, 2) not null,
    payment_state  varchar(50)    not null,
    shipment_state varchar(50)    not null,
    state          varchar(50)    not null,
    user_id        bigint,
    address_id     bigint         not null,
    constraint pk_order primary key (id),
    constraint fk_order1 foreign key (user_id) references users (id) on delete set null,
    constraint fk_order2 foreign key (address_id) references address (id) on delete cascade
);
