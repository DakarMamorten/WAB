create or replace procedure merge_and_delete_cart(
    p_source_cart_id varchar,
    p_user_id bigint,
    p_cart_id out varchar,
    p_total out bigint
)
    language plpgsql
as
$$
declare
    l_target_cart_id uuid;
    l_source_cart_id uuid = p_source_cart_id;
begin
    select c.id
    into l_target_cart_id
    from cart as c
    where c.user_id = p_user_id;

    if p_user_id is not null then
        if l_target_cart_id is null and l_source_cart_id is null then
            p_total = 0;
            p_cart_id = null;
            return;
        end if;
        if l_target_cart_id is null and l_source_cart_id is not null then
            update cart as c set user_id = p_user_id, expire_date = null where c.id = l_source_cart_id;
            select coalesce(sum(ci2.quantity), 0)
            into p_total
            from cart_item as ci2
            where ci2.cart_id = l_source_cart_id;
            p_cart_id = l_source_cart_id::varchar;
            return;
        end if;
        if l_target_cart_id is not null and l_source_cart_id is null then
            p_cart_id = l_target_cart_id::varchar;
            select coalesce(sum(ci2.quantity), 0)
            into p_total
            from cart_item as ci2
            where ci2.cart_id = l_target_cart_id;
            return;
        end if;
    else
        if p_source_cart_id is null then
            p_total = 0;
            p_cart_id = null;
            return;
        end if;
        p_cart_id = l_source_cart_id::varchar;
        select coalesce(sum(ci2.quantity), 0)
        into p_total
        from cart_item as ci2
        where ci2.cart_id = l_source_cart_id;
        return;
    end if;

    --merge data from source cart into target cart in the cart_item table
    merge into cart_item ci
    using (select l_target_cart_id  as cart_id,
                  cir.product_id,
                  sum(cir.quantity) as quantity
           from cart_item cir
           where cir.cart_id in (l_source_cart_id, l_target_cart_id)
           group by cir.product_id) as s
    on (ci.product_id = s.product_id and ci.cart_id = s.cart_id)
    when matched then
        update set quantity = s.quantity
    when not matched then
        insert (cart_id, product_id, quantity)
        values (s.cart_id, s.product_id, s.quantity);

    -- delete the source cart items from the cart_item table
    delete
    from cart_item
    where cart_id = l_source_cart_id
      and l_target_cart_id != l_source_cart_id;

    -- delete the source cart from the cart table
    delete
    from cart
    where id = l_source_cart_id
      and l_target_cart_id != l_source_cart_id;

    select sum(ci2.quantity)
    into p_total
    from cart_item as ci2
    where ci2.cart_id = l_target_cart_id;
    p_cart_id = l_target_cart_id::varchar;

    raise notice 'merged data from source_cart_id % to target_cart_id % and deleted source cart.', l_source_cart_id, l_target_cart_id;
end;
$$;
