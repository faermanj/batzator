# Things schema
 
# --- !Ups

CREATE TABLE Thing (
    uuid char(36) NOT NULL,
    code varchar(255),
    description text,
);
 
# --- !Downs
 
DROP TABLE Thing;
