# Things schema
 
# --- !Ups

CREATE TABLE Thing (
    uuid char(36) not null primary key,
    code varchar(255),
    description text
);
 
# --- !Downs
 
DROP TABLE Thing;
