create table if not exists cats
(
    "idCat" serial not null
        constraint persons_pkey
            primary key,
    name varchar(30),
    numberphone varchar(10),
    deliveryaddress varchar(100),
    color varchar(255)
);


create table cats_owners
(
    id     serial not null
        constraint cats_owners_pkey
            primary key,
    name   varchar(100),
    cat_id integer
        constraint cats_owners_cat_id_fkey
            references cats
);


INSERT INTO public.cats ("idCat", name, numberphone, deliveryaddress, color) VALUES (DEFAULT, 'Jojo', null, null, null);

CREATE PROCEDURE insert_data(name varchar)
    LANGUAGE SQL
AS $$
INSERT INTO cats_owners(id, name) VALUES (default, name);
$$;

CALL insert_data('naughty boy');