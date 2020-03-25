create table PinguinUser (
    Id bigint identity primary key,
    Name varchar not null,
    unique(Id)
);

create table Issue  (
    Id bigint identity primary key,
    Title varchar not null,
    Description varchar,
    CreationDate date default Current_Date() not null,
    AssigneeId bigint,
    foreign key (AssigneeId) references PinguinUser(Id)
);

create table Story (
    Id bigint identity primary key,
    IssueId bigint not null,
    Status varchar not null,
    Estimate int,
    foreign key (IssueId) references Issue(Id)
);

create table Bug (
    Id bigint identity primary key,
    IssueId bigint not null,
    Status varchar not null,
    Priority varchar not null,
    foreign key (IssueId) references Issue(Id)
);