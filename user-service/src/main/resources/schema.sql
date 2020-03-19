drop table PinguinUser if exists;

create table PinguinUser (
    Id bigint identity primary key,
    Name varchar(100) not null,
    unique(Id)
);
