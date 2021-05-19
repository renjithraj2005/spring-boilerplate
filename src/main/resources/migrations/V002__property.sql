create table property (
   	id bigint not null auto_increment,
    created_at bigint not null,
    updated_at bigint not null,
    property_name varchar(30) not null,
    description varchar(30) not null,
    location varchar(16) not null,
    is_approved boolean not null default false,
    primary key (id)
) engine=InnoDB;