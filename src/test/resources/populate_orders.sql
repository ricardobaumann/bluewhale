delete from order_items;
delete from products;
delete from orders;

insert into products values (gen_random_uuid(),'foo', 10);
insert into products values (gen_random_uuid(),'bar', 5);

insert into orders (id) values (gen_random_uuid());

insert into order_items (id, order_id, product_id, amount)
values (
    gen_random_uuid(),
    (select id from orders limit 1),
    (select id from products where name = 'foo'),
    10
);

insert into order_items (id, order_id, product_id, amount)
values (
    gen_random_uuid(),
    (select id from orders limit 1),
    (select id from products where name = 'bar'),
    20
)