drop table posts;
create table posts (
    id serial primary key,
    title text,
    href text unique,
    descr text,
    date timestamp
);
