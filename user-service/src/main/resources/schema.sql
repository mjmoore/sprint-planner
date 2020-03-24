drop table PinguinUser if exists;

create table PinguinUser (
    Id bigint identity primary key,
    Name varchar not null,
    unique(Id)
);
