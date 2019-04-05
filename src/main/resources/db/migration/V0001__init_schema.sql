--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Debian 11.2-1.pgdg90+1)

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

-- Drop table

-- DROP TABLE public.customers;

CREATE TABLE public.customers (
    id bigserial NOT NULL,
    address varchar(255) NULL,
    email varchar(255) NULL,
    "name" varchar(255) NULL,
    phone varchar(255) NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

-- Permissions

-- ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: wedding_ceremony; Type: TABLE; Schema: public; Owner: postgres
--

-- Drop table

-- DROP TABLE public.wedding_ceremony;

CREATE TABLE public.wedding_ceremony (
    id bigserial NOT NULL,
    creation_time timestamp NULL,
    modification_time timestamp NULL,
    advance numeric(19,2) NULL,
    charge numeric(19,2) NULL,
    church varchar(255) NULL,
    date_of_event date NULL,
    date_of_signing date NULL,
    document_short_name varchar(255) NULL,
    payment_method varchar(255) NULL,
    performer varchar(255) NULL,
    place_of_event varchar(255) NULL,
    place_of_signing varchar(255) NULL,
    service_package varchar(255) NULL,
    time_of_event time NULL,
    customer_id int8 NULL,
    CONSTRAINT wedding_ceremony_pkey PRIMARY KEY (id),
    CONSTRAINT wedding_ceremony_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Permissions

-- ALTER TABLE public.wedding_ceremony OWNER TO postgres;

