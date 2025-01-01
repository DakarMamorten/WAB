create table if not exists order_item
(
    order_id               uuid    not null,
    product_id             bigint  not null,
    quantity               int     not null,
    price_at_time_of_order numeric not null,
    constraint pk_order_item primary key (order_id, product_id),
    constraint fk_order_item1 foreign key (order_id) references orders (id),
    constraint fk_order_item2 foreign key (product_id) references product (id)
);

create index idx_order_item1 on order_item (order_id);
create index idx_order_item2 on order_item (product_id);
