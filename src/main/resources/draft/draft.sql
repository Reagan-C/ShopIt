 create table admin (
        id bigint not null auto_increment,
        primary key (id)
    ) engine=InnoDB


    create table cart (
        id bigint not null auto_increment,
        primary key (id)
    ) engine=InnoDB


    create table category (
        id bigint not null auto_increment,
        abbreviaton varchar(255) not null,
        category_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB


    create table country (
        id bigint not null auto_increment,
        abbreviation varchar(255) not null,
        created_on datetime(6) not null,
        title varchar(255) not null,
        updated_on datetime(6) not null,
        primary key (id)
    ) engine=InnoDB


    create table fulfilled_orders (
        id bigint not null auto_increment,
        created_on datetime(6) not null,
        updated_on datetime(6) not null,
        user_id bigint,
        primary key (id)
    ) engine=InnoDB


    create table items (
        id bigint not null auto_increment,
        created_on datetime(6) not null,
        description varchar(255) not null,
        name varchar(255) not null,
        picture varchar(255) not null,
        price float(53) not null,
        quantity bigint not null,
        updated_on datetime(6) not null,
        category_id bigint,
        cart_id bigint,
        primary key (id)
    ) engine=InnoDB


    create table otp (
        id bigint not null auto_increment,
        otp varchar(255) not null,
        user_id bigint,
        primary key (id)
    ) engine=InnoDB


    create table pending_order (
        id bigint not null auto_increment,
        created_on datetime(6) not null,
        cart_id bigint,
        user_id bigint,
        fulfilled_orders_id bigint,
        primary key (id)
    ) engine=InnoDB


    create table roles (
        id bigint not null auto_increment,
        code varchar(255) not null,
        created_on datetime(6) not null,
        title varchar(255) not null,
        updated_on datetime(6) not null,
        primary key (id)
    ) engine=InnoDB


    create table user (
        id bigint not null auto_increment,
        address varchar(255),
        authentication_token varchar(255),
        city varchar(255),
        created_on datetime(6) not null,
        date_of_birth date,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        password varchar(255),
        phone_number varchar(255),
        state varchar(255),
        updated_on datetime(6) not null,
        username varchar(255),
        cart_id bigint,
        country_id bigint,
        admin_id bigint,
        primary key (id)
    ) engine=InnoDB


    create table user_roles (
        user_id bigint not null,
        roles_id bigint not null,
        primary key (user_id, roles_id)
    ) engine=InnoDB


    alter table items
       drop index UK_k80eopbv6al4nrtmr0qmd3tap


    alter table items
       add constraint UK_k80eopbv6al4nrtmr0qmd3tap unique (description)


    alter table otp
       drop index UK_4mkxc1wpojj1vymcvurokktwm


    alter table otp
       add constraint UK_4mkxc1wpojj1vymcvurokktwm unique (user_id)


    alter table pending_order
       drop index UK_7wcy6kkadcssimtn4a9dyd99p


    alter table pending_order
       add constraint UK_7wcy6kkadcssimtn4a9dyd99p unique (cart_id)


    alter table user
       drop index UK_47dq8urpj337d3o65l3fsjph3


    alter table user
       add constraint UK_47dq8urpj337d3o65l3fsjph3 unique (cart_id)


    alter table fulfilled_orders
       add constraint FK60csxcjl3dmvj5cxpll5ren59
       foreign key (user_id)
       references user (id)


    alter table items
       add constraint FKmwj262911sqtm7lw9yhmf125
       foreign key (category_id)
       references category (id)


    alter table items
       add constraint FKi2x4un59w0vfyu313h4m7gm25
       foreign key (cart_id)
       references cart (id)


    alter table otp
       add constraint FKdrrkob03otk15fxe9b0bkkp35
       foreign key (user_id)
       references user (id)


    alter table pending_order
       add constraint FKhml69xkmqtxxiafiy6jdjl3ct
       foreign key (cart_id)
       references cart (id)


    alter table pending_order
       add constraint FKhvw9qxa18uevsl3d2ma819oq0
       foreign key (user_id)
       references user (id)


    alter table pending_order
       add constraint FKpep0yt4r4ur9f7hyt0pl6yt6w
       foreign key (fulfilled_orders_id)
       references fulfilled_orders (id)


    alter table user
       add constraint FKtqa69bib34k2c0jhe7afqsao6
       foreign key (cart_id)
       references cart (id)


    alter table user
       add constraint FKge8lxibk9q3wf206s600otk61
       foreign key (country_id)
       references country (id)


    alter table user
       add constraint FKpi0nc9w2flbfset55lce6a63f
       foreign key (admin_id)
       references admin (id)


    alter table user_roles
       add constraint FKdbv8tdyltxa1qjmfnj9oboxse
       foreign key (roles_id)
       references roles (id)


    alter table user_roles
       add constraint FK55itppkw3i07do3h7qoclqd4k
       foreign key (user_id)
       references user (id)