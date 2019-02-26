--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Debian 11.2-1.pgdg90+1)

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    id bigint NOT NULL,
    address character varying(255),
    email character varying(255),
    name character varying(255),
    phone character varying(255)
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_id_seq OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- Name: wedding_ceremony; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wedding_ceremony (
    id bigint NOT NULL,
    creation_time timestamp without time zone,
    modification_time timestamp without time zone,
    advance numeric(19,2),
    charge numeric(19,2),
    church character varying(255),
    date_of_event date,
    date_of_signing date,
    document_short_name character varying(255),
    payment_method character varying(255),
    performer character varying(255),
    place_of_event character varying(255),
    place_of_signing character varying(255),
    service_package character varying(255),
    time_of_event time without time zone,
    customer_id bigint
);


ALTER TABLE public.wedding_ceremony OWNER TO postgres;

--
-- Name: wedding_ceremony_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wedding_ceremony_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wedding_ceremony_id_seq OWNER TO postgres;

--
-- Name: wedding_ceremony_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wedding_ceremony_id_seq OWNED BY public.wedding_ceremony.id;


--
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- Name: wedding_ceremony id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedding_ceremony ALTER COLUMN id SET DEFAULT nextval('public.wedding_ceremony_id_seq'::regclass);


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_id_seq', 1, false);


--
-- Name: wedding_ceremony_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wedding_ceremony_id_seq', 1, false);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: wedding_ceremony wedding_ceremony_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedding_ceremony
    ADD CONSTRAINT wedding_ceremony_pkey PRIMARY KEY (id);


--
-- Name: wedding_ceremony fkodv045lfykmyigt3r8oaq7g4t; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedding_ceremony
    ADD CONSTRAINT wedding_ceremony_customer_id FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- PostgreSQL database dump complete
--

