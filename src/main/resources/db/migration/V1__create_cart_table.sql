create table if not exists cart
(
    id          uuid not null,
    user_id     bigint,
    expire_date timestamp,
    constraint pk_cart primary key (id),
    constraint id_expire_date_check check
        (
        (user_id is null and expire_date is not null)
            or
        (user_id is not null and expire_date is null)
        )
);
