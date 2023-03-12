CREATE SCHEMA IF NOT EXISTS dbunit;

DROP TABLE IF EXISTS doctors, patients;
DROP SEQUENCE IF EXISTS doctors_d_id_seq;
DROP SEQUENCE IF EXISTS patients_p_id_seq;


CREATE SEQUENCE doctors_d_id_seq START WITH 10 INCREMENT BY 1;

CREATE TABLE DOCTORS
(
    id bigint primary key DEFAULT NEXTVAL('doctors_d_id_seq'),
    name varchar(255) not null,
    surname varchar(255) not null,
    position varchar(255)
);

CREATE SEQUENCE patients_p_id_seq START WITH 10 INCREMENT BY 1;

CREATE TABLE PATIENTS
(
    id integer primary key DEFAULT NEXTVAL('patients_p_id_seq'),
    name varchar(255) not null,
    surname varchar(255),
    age int not null
);




