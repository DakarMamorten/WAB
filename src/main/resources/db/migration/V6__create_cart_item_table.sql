create table if not exists cart_item
(
    cart_id    uuid   not null,
    product_id bigint not null,
    quantity   int    not null,
    constraint pk_cart_item primary key (cart_id, product_id),
    constraint fk_cart_item1 foreign key (cart_id) references cart (id),
    constraint fk_cart_item2 foreign key (product_id) references product (id)
);

create index idx_cart_item1 on cart_item (cart_id);
create index idx_cart_item2 on cart_item (product_id);
