create database shippermanagement;

create table if not exists admins
(
    id       int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(20) not null
);

create table if not exists shippers
(
    shipper_id     int auto_increment
        primary key,
    firstname      varchar(255) not null,
    lastname       varchar(255) not null,
    phone          varchar(20)  not null,
    password       varchar(20)  not null,
    birthDay       varchar(20)  not null,
    email          varchar(255) not null,
    address        varchar(255) null,
    personal_image longblob     null
);

create table if not exists orders
(
    order_id             int auto_increment
        primary key,
    shipper_id           int            null,
    pickup_location      varchar(255)   not null,
    delivery_location    varchar(255)   not null,
    value                decimal(10, 2) not null,
    other_details        text           null,
    delivery_date_expect varchar(20)    null,
    constraint orders_ibfk_1
        foreign key (shipper_id) references shippers (shipper_id)
);

create table if not exists delivery_note
(
    delivery_id     int auto_increment
        primary key,
    order_id        int                                null,
    delivery_number int                                null,
    delivery_status enum ('Delivered', 'Not Delivery', 'Delivered, wait Admin confirm!') null,
    delivery_date   varchar(20)                        null,
    constraint delivery_note_ibfk_2
        foreign key (order_id) references orders (order_id)
);