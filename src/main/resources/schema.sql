

 create table IF NOT EXISTS `admin` (
        `id` bigint not null auto_increment,
        primary key (`id`)
    );


    create table IF NOT EXISTS `cart` (
        `id` bigint not null auto_increment,
        primary key (`id`)
    );


    create table IF NOT EXISTS `category` (
        `id` bigint not null auto_increment,
        `abbreviation` varchar(255) not null,
        `category_name` varchar(255) not null,
        primary key (`id`)
    );


    create table IF NOT EXISTS `country` (
        `id` bigint not null auto_increment,
        `abbreviation` varchar(255) not null,
        `created_on` datetime(6) not null,
        `title` varchar(255) not null,
        `updated_on` datetime(6) not null,
        primary key (`id`)
    );


    create table IF NOT EXISTS `fulfilled_orders` (
        `id` bigint not null auto_increment,
        `created_on` datetime(6) not null,
        `updated_on` datetime(6) not null,
        `user_id` bigint,
        primary key (`id`)
    );


    create table IF NOT EXISTS `items` (
        `id` bigint not null auto_increment,
        `created_on` datetime(6) not null,
        `description` varchar(255) not null,
        `name` varchar(255) not null,
        `picture` varchar(255) not null,
        `price` float(53) not null,
        `quantity` bigint not null,
        `updated_on` datetime(6) not null,
        `category_id` bigint,
        `cart_id` bigint,
        primary key (`id`)
    );


    create table IF NOT EXISTS `auth_token` (
        `id` bigint not null auto_increment,
        `token` varchar(255) not null,
        `created_on` datetime(6) not null,
        `modified_on` datetime(6),
        `user_id` bigint,
        primary key (`id`)
    );


    create table IF NOT EXISTS `pending_order` (
        `id` bigint not null auto_increment,
        `created_on` datetime(6) not null,
        `cart_id` bigint,
        `user_id` bigint,
        `fulfilled_orders_id` bigint,
        primary key (`id`)
    );


    create table IF NOT EXISTS `roles` (
        `id` bigint not null auto_increment,
        `code` varchar(255) not null,
        `created_on` datetime(6) not null,
        `title` varchar(255) not null,
        `updated_on` datetime(6) not null,
        primary key (`id`)
    );


    create table IF NOT EXISTS `user` (
        `id` bigint not null auto_increment,
        `address` varchar(255),
        `authentication_token` varchar(255),
        `city` varchar(255),
        `created_on` datetime(6) not null,
        `date_of_birth` date,
        `email` varchar(255),
        `first_name` varchar(255),
        `last_name` varchar(255),
        `password` varchar(255),
        `phone_number` varchar(255),
        `confirmed` BOOLEAN not null DEFAULT FALSE,
        `state` varchar(255),
        `updated_on` datetime(6) not null,
        `username` varchar(255),
        `cart_id` bigint,
        `country_id` bigint,
        `admin_id` bigint,
        primary key (`id`)
    );


    create table IF NOT EXISTS `user_roles` (
        `user_id` bigint not null,
        `roles_id` bigint not null,
        primary key (`user_id`, `roles_id`)
    );


