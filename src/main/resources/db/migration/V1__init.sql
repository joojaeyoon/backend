
create table account(
    id int AUTO_INCREMENT,
    username varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(32) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    primary key (id)
);

create table post(
    id int AUTO_INCREMENT,
    account_id int,
    category VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    price int NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    completed boolean,
    primary key(id),
    foreign key(account_id) references account(id) on delete CASCADE
);

create table post_img(
    id int AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    post_id int,
    primary key(id),
    foreign key(post_id) references post(id) on delete CASCADE
);

create table comment(
    id int AUTO_INCREMENT,
    account_id int,
    post_id int,
    content VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    primary key(id),
    foreign key(account_id) references account(id) on delete CASCADE,
    foreign key(post_id) references post(id) on delete CASCADE
);