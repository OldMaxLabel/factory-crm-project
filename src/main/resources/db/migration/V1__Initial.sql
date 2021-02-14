-- create sequence hibernate_sequence start 1 increment 1;

create table machine
(
    id          int8 generated by default as identity,
    description varchar(300),
    processing  varchar(255) not null,
    state       varchar(255) not null,
    title       varchar(50)  not null,
    x_axis      int4         not null check (x_axis >= 1),
    y_axis      int4         not null check (y_axis >= 1),
    z_axis      int4         not null check (z_axis >= 1),
    primary key (id)
);

create table message
(
    id              int8 generated by default as identity,
    local_date_time timestamp,
    tag             varchar(10) not null,
    text            varchar(500),
    user_id         int8,
    primary key (id)
);

create table operation
(
    id       int8 generated by default as identity,
    duration int4         not null check (duration >= 1),
    title    varchar(255) not null,
    task_id  int8,
    primary key (id)
);

create table stock
(
    id       int8 generated by default as identity,
    diameter float8       not null,
    form     varchar(255) not null,
    height   float8       not null,
    length   float8       not null,
    material varchar(255) not null,
    width    float8       not null,
    primary key (id)
);

create table stuff
(
    id       int8 generated by default as identity,
    diameter float8       not null,
    form     varchar(255) not null,
    height   float8       not null,
    length   float8       not null,
    material varchar(255) not null,
    width    float8       not null,
    primary key (id)
);

create table task
(
    id              int8 generated by default as identity,
    comments        varchar(350),
    count           int4         not null check (count >= 1),
    end_date        date,
    filename        varchar(255) not null,
    finalize_date   date         not null,
    initialize_date date         not null,
    title           varchar(255) not null,
    stock_id        int8,
    primary key (id)
);

create table tasks_machines
(
    task_id    int8 not null,
    machine_id int8 not null,
    primary key (task_id, machine_id)
);

create table tasks_tools
(
    task_id int8 not null,
    tool_id int8 not null,
    primary key (task_id, tool_id)
);

create table tool
(
    id             int8 generated by default as identity,
    count          int4         not null check (count >= 0),
    cutting_length float8       not null,
    diameter       float8       not null,
    length         float8       not null,
    specialization varchar(255) not null,
    title          varchar(255) not null,
    primary key (id)
);

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usr
(
    id         int8 generated by default as identity,
    active     boolean      not null,
    email      varchar(255),
    firstname  varchar(255),
    password   varchar(255) not null,
    phone      varchar(255),
    secondname varchar(255),
    username   varchar(255) not null,
    primary key (id)
);

alter table if exists message
    add constraint message_user_fk
    foreign key (user_id) references usr;

alter table if exists operation
    add constraint operation_task_fk
    foreign key (task_id) references task;

alter table if exists task
    add constraint task_stock_fk
    foreign key (stock_id) references stock;

alter table if exists tasks_machines
    add constraint machine_task_machine_fk
    foreign key (machine_id) references machine;

alter table if exists tasks_machines
    add constraint task_machine_task_fk
    foreign key (task_id) references task;

alter table if exists tasks_tools
    add constraint tool_task_tool_fk
    foreign key (tool_id) references tool;

alter table if exists tasks_tools
    add constraint task_tool_task_fk
    foreign key (task_id) references task;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;