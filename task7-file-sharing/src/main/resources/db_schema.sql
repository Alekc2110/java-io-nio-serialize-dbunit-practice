DROP TABLE IF EXISTS files;
DROP PROCEDURE IF EXISTS expire_file(file_id INTEGER);

CREATE TABLE files(
    id serial primary key,
    file_name varchar(50),
    file_data bytea,
    expiration_date date
);

CREATE or REPLACE PROCEDURE expire_file(
    IN file_id INTEGER,
    OUT out_result BOOLEAN)
AS $$
BEGIN
    UPDATE files SET expiration_date = NOW() WHERE id = file_id;
    commit;
    out_result := true;
END
$$ LANGUAGE plpgsql;