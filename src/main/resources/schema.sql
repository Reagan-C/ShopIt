

 CREATE TABLE IF NOT EXISTS `admin` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `user_id` BIGINT NOT NULL,
        `user_email` VARCHAR(255),
        `user_first_name` VARCHAR(255),
        `user_last_name` VARCHAR(255),
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `cart` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `user_id` BIGINT,
        `total_price` DOUBLE,
        PRIMARY KEY (`id`)
    );

    CREATE TABLE IF NOT EXISTS `cart_item` (
            `id` BIGINT NOT NULL AUTO_INCREMENT,
            `cart_id`   BIGINT,
            `item_id` BIGINT,
            `count_of_items`  INT,
            `cart_item_price`  DOUBLE,
            PRIMARY KEY (`id`)
        );


    CREATE TABLE IF NOT EXISTS `category` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `category_name` VARCHAR(255) NOT NULL,
        `abbreviation` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `country` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `title` VARCHAR(255) NOT NULL,
        `abbreviation` VARCHAR(255) NOT NULL,
        `added_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `fulfilled_orders` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `user_id` BIGINT,
        `token` VARCHAR (255),
        `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `confirmed_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `items` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        `description` VARCHAR(255) NOT NULL,
        `price` FLOAT(53) NOT NULL,
        `quantity` INT NOT NULL,
        `picture` VARCHAR(255) NOT NULL,
        `category_id` BIGINT,
        `added_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `auth_token` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `token` VARCHAR(255) NOT NULL,
        `user_id` BIGINT,
        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `confirmed_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `expires_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,

        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `pending_order` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `confirmed` BOOLEAN NOT NULL DEFAULT FALSE,
        `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `token` VARCHAR (255),
        `cart_id` BIGINT,
        `user_id` BIGINT,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `roles` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `code` VARCHAR(255) NOT NULL,
        `title` VARCHAR(255) NOT NULL,
        `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `user` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `first_name` VARCHAR(255),
        `last_name` VARCHAR(255),
        `email` VARCHAR(255),
        `address` VARCHAR(255),
        `phone_number` VARCHAR(255),
        `state` VARCHAR(255),
        `username` VARCHAR(255),
        `enabled` BOOLEAN NOT NULL DEFAULT FALSE,
        `city` VARCHAR(255),
        `date_of_birth` date,
        `password` VARCHAR(255),
        `authentication_token` VARCHAR(255),
        `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
        `cart_id` BIGINT,
        `country_id` BIGINT,
        PRIMARY KEY (`id`)
    );


    CREATE TABLE IF NOT EXISTS `user_roles` (
        `user_id` BIGINT NOT NULL,
        `roles_id` BIGINT NOT NULL,
        PRIMARY KEY (`user_id`, `roles_id`)
    );


