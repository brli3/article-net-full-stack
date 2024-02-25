-- create database
# drop database if exists article_net;
#
# create database article_net;

-- use database
use article_net;

-- user table
create table user (
                      id int unsigned primary key auto_increment comment 'ID',
                      username varchar(20) not null unique comment 'username',
                      password varchar(32)  comment 'password',
                      nickname varchar(10)  default '' comment 'nickname',
                      email varchar(128) default '' comment 'email',
                      user_pic varchar(128) default '' comment 'avatar url',
                      create_time datetime not null comment 'create time',
                      update_time datetime not null comment 'update time'
) comment 'user table';

-- category table
create table category(
                         id int unsigned primary key auto_increment comment 'ID',
                         category_name varchar(32) not null comment 'category name',
                         category_alias varchar(32) not null comment 'alias',
                         create_user int unsigned not null comment 'creator ID',
                         create_time datetime not null comment 'create time',
                         update_time datetime not null comment 'update time',
                         constraint fk_category_user foreign key (create_user) references user(id) -- foreign key
);

-- article table
create table article(
                        id int unsigned primary key auto_increment comment 'ID',
                        title varchar(32) not null comment 'article title',
                        content varchar(10000) not null comment 'article content',
                        cover_img varchar(128) not null  comment 'cover image url',
                        state varchar(12) default 'draft' comment 'state: can only be [published] or [draft]',
                        category_id int unsigned comment 'category ID',
                        create_user int unsigned not null comment 'creator ID',
                        create_time datetime not null comment 'create time',
                        update_time datetime not null comment 'update time',
                        constraint fk_article_category foreign key (category_id) references category(id),-- foreign key
                        constraint fk_article_user foreign key (create_user) references user(id) -- foreign key
);