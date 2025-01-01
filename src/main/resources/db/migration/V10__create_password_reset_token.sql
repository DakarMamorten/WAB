create table password_reset_token
(
    user_id     bigint       not null,
    token       varchar(255) not null,
    expiry_date timestamp    not null,
    constraint pk_password_reset_token primary key (user_id),
    constraint fk_user foreign key (user_id) references users (id) on delete cascade,
    constraint uq_password_reset_token unique (token)
);
