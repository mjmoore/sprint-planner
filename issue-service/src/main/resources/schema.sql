create table Issue  (
    Id bigint identity primary key,
    Title varchar not null,
    Description varchar,
    CreationDate date default Current_Date() not null,
    AssigneeId bigint
);

create table Story (
    Id bigint identity primary key,
    IssueId bigint not null,
    Status varchar not null,
    Priority varchar not null,
    foreign key (IssueId) references Issue(id)
);

create table Bug (
    Id bigint identity primary key,
    IssueId bigint not null,
    Status varchar not null,
    Priority varchar not null,
    foreign key (IssueId) references Issue(id)
);
