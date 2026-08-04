--
-- PostgreSQL database cluster dump
--

-- Started on 2026-03-14 03:44:07

\restrict zq3wHhegHrwXU9agoOUIhoGyYUpxTwxkzP2VKQRRDiEI7tBGWbt2tRiV0YrJbYc

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE jasperdb;
ALTER ROLE jasperdb WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--






\unrestrict zq3wHhegHrwXU9agoOUIhoGyYUpxTwxkzP2VKQRRDiEI7tBGWbt2tRiV0YrJbYc

--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

\restrict bt4sIzY9S0Ujt6Fu9PwO1OSOz52mWHnbQ5D5Q6Nt1M3GrLj1JKCY904o6mToHLs

-- Dumped from database version 12.4
-- Dumped by pg_dump version 18.2

-- Started on 2026-03-14 03:44:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2812 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2026-03-14 03:44:12

--
-- PostgreSQL database dump complete
--

\unrestrict bt4sIzY9S0Ujt6Fu9PwO1OSOz52mWHnbQ5D5Q6Nt1M3GrLj1JKCY904o6mToHLs

--
-- Database "db_medicall" dump
--

--
-- PostgreSQL database dump
--

\restrict ehNneJJHQK2Ufp7NVgXHM7MzEkF80Dn0KPbX5D2kw9gaTSKtc5Mar3oA2Bqffdz

-- Dumped from database version 12.4
-- Dumped by pg_dump version 18.2

-- Started on 2026-03-14 03:44:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2841 (class 1262 OID 26968)
-- Name: db_medicall; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db_medicall WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE db_medicall OWNER TO postgres;

\unrestrict ehNneJJHQK2Ufp7NVgXHM7MzEkF80Dn0KPbX5D2kw9gaTSKtc5Mar3oA2Bqffdz
\connect db_medicall
\restrict ehNneJJHQK2Ufp7NVgXHM7MzEkF80Dn0KPbX5D2kw9gaTSKtc5Mar3oA2Bqffdz

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 26969)
-- Name: tbl_doktor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_doktor (
    id_doktor integer,
    nama_doktor character varying,
    spesialisasi character varying
);


ALTER TABLE public.tbl_doktor OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 26977)
-- Name: tbl_jadwal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_jadwal (
    id_jadwal integer NOT NULL,
    id_doktor integer,
    tanggal date,
    jam time without time zone,
    status character varying(20) DEFAULT 'AVAILABLE'::character varying
);


ALTER TABLE public.tbl_jadwal OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 26975)
-- Name: tbl_jadwal_id_jadwal_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_jadwal_id_jadwal_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tbl_jadwal_id_jadwal_seq OWNER TO postgres;

--
-- TOC entry 2843 (class 0 OID 0)
-- Dependencies: 203
-- Name: tbl_jadwal_id_jadwal_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_jadwal_id_jadwal_seq OWNED BY public.tbl_jadwal.id_jadwal;


--
-- TOC entry 206 (class 1259 OID 26988)
-- Name: tbl_reservasi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_reservasi (
    id integer NOT NULL,
    nama_pasien character varying(100),
    email_pasien character varying(100),
    id_doktor integer,
    id_jadwal integer
);


ALTER TABLE public.tbl_reservasi OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 26986)
-- Name: tbl_reservasi_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_reservasi_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tbl_reservasi_id_seq OWNER TO postgres;

--
-- TOC entry 2844 (class 0 OID 0)
-- Dependencies: 205
-- Name: tbl_reservasi_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_reservasi_id_seq OWNED BY public.tbl_reservasi.id;


--
-- TOC entry 2698 (class 2604 OID 26980)
-- Name: tbl_jadwal id_jadwal; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_jadwal ALTER COLUMN id_jadwal SET DEFAULT nextval('public.tbl_jadwal_id_jadwal_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 26991)
-- Name: tbl_reservasi id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_reservasi ALTER COLUMN id SET DEFAULT nextval('public.tbl_reservasi_id_seq'::regclass);


--
-- TOC entry 2831 (class 0 OID 26969)
-- Dependencies: 202
-- Data for Name: tbl_doktor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_doktor (id_doktor, nama_doktor, spesialisasi) FROM stdin;
1	Dr Budi	Jantung
2	Dr Siti	Kulit
3	Dr Andi	Anak
\.


--
-- TOC entry 2833 (class 0 OID 26977)
-- Dependencies: 204
-- Data for Name: tbl_jadwal; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_jadwal (id_jadwal, id_doktor, tanggal, jam, status) FROM stdin;
4	3	2026-03-22	13:00:00	AVAILABLE
2	1	2026-03-20	11:00:00	AVAILABLE
1	1	2026-03-20	10:00:00	AVAILABLE
3	2	2026-03-21	09:00:00	AVAILABLE
\.


--
-- TOC entry 2835 (class 0 OID 26988)
-- Dependencies: 206
-- Data for Name: tbl_reservasi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_reservasi (id, nama_pasien, email_pasien, id_doktor, id_jadwal) FROM stdin;
\.


--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 203
-- Name: tbl_jadwal_id_jadwal_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_jadwal_id_jadwal_seq', 4, true);


--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 205
-- Name: tbl_reservasi_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_reservasi_id_seq', 5, true);


--
-- TOC entry 2702 (class 2606 OID 26983)
-- Name: tbl_jadwal tbl_jadwal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_jadwal
    ADD CONSTRAINT tbl_jadwal_pkey PRIMARY KEY (id_jadwal);


--
-- TOC entry 2704 (class 2606 OID 26994)
-- Name: tbl_reservasi tbl_reservasi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_reservasi
    ADD CONSTRAINT tbl_reservasi_pkey PRIMARY KEY (id);


--
-- TOC entry 2842 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2026-03-14 03:44:16

--
-- PostgreSQL database dump complete
--

\unrestrict ehNneJJHQK2Ufp7NVgXHM7MzEkF80Dn0KPbX5D2kw9gaTSKtc5Mar3oA2Bqffdz

-- Completed on 2026-03-14 03:44:16

--
-- PostgreSQL database cluster dump complete
--

