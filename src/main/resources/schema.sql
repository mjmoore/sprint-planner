create table Pinguin_User (
    Id bigint identity primary key,
    Name varchar not null,
    unique(Id)
);

create table Issue  (
    Id bigint identity primary key,
    Title varchar not null,
    Description varchar,
    Creation_Date date default Current_Date() not null,
    Assignee_Id bigint,
    foreign key (Assignee_Id) references Pinguin_User(Id)
);

create table Story (
    Id bigint identity primary key,
    Issue_Id bigint not null,
    Status varchar not null,
    Estimate int,
    foreign key (Issue_Id) references Issue(Id)
);

create table Bug (
    Id bigint identity primary key,
    Issue_Id bigint not null,
    Status varchar not null,
    Priority varchar not null,
    foreign key (Issue_Id) references Issue(Id)
);