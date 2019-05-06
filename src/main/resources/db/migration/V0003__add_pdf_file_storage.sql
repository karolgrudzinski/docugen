CREATE TABLE public.wedding_pdf (
    id bigserial NOT NULL,
    creation_time timestamp NULL,
    modification_time timestamp NULL,
    uuid uuid NOT NULL,
    data bytea NULL,
    file_name varchar(255) NULL,
    CONSTRAINT wedding_pdf_pkey PRIMARY KEY (id)
);

ALTER TABLE public.wedding_ceremony ADD COLUMN file_id bigint;
ALTER TABLE public.wedding_ceremony ADD CONSTRAINT wedding_pdf_id FOREIGN KEY (file_id) REFERENCES public.wedding_pdf(id);