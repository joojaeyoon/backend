
create table account(
    id int AUTO_INCREMENT,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

create table board(
    id int AUTO_INCREMENT,
    account_id int,
    category VARCHAR(255) not null,
    title VARCHAR(255) not null,
    content VARCHAR(255) not null,
    created_at DATETIME,
    updated_at DATETIME,
    primary key(id),
    foreign key(account_id) references account(id) on delete CASCADE
);

create table comment(
    id int AUTO_INCREMENT,
    account_id int,
    board_id int,
    content VARCHAR(255) not null,
    created_at DATETIME,
    updated_at DATETIME,
    primary key(id),
    foreign key(account_id) references account(id) on delete CASCADE,
    foreign key(board_id) references board(id) on delete CASCADE
);