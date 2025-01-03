create table product
(
    id               bigserial,
    name             varchar(255)   not null,
    description      text,
    price            decimal(10, 2) not null,
    image_path       varchar(255)   not null,
    product_brand_id bigint         not null,
    constraint pk_product primary key (id),
    constraint fk_product foreign key (product_brand_id) references product_brand (id)
);

create index idx_product on product (product_brand_id);
