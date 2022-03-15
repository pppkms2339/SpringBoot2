--DROP TABLE IF EXISTS todo;
CREATE TABLE IF NOT EXISTS todo
(
    id varchar(36) not null primary key,
    description varchar(255) not null,
    created timestamp,
    updated timestamp,
    completed boolean
);