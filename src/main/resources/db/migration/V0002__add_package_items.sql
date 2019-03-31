-- Drop table

-- DROP TABLE public.package_items;

CREATE TABLE public.package_items (
    id bigserial NOT NULL,
    name varchar(255) NULL,
    CONSTRAINT package_items_pkey PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.package_items OWNER TO postgres;

-- Drop table

-- DROP TABLE public.wedding_ceremony_package_items;

CREATE TABLE public.wedding_ceremony_package_items (
    package_item_id int8 NOT NULL,
    wedding_ceremony_id int8 NOT NULL,
    CONSTRAINT wedding_ceremony_package_items_pkey PRIMARY KEY (package_item_id, wedding_ceremony_id),
    FOREIGN KEY (wedding_ceremony_id) REFERENCES wedding_ceremony(id),
    FOREIGN KEY (package_item_id) REFERENCES package_items(id)
);

-- Permissions

-- ALTER TABLE public.wedding_ceremony_package_items OWNER TO postgres;