CREATE TABLE users (
  id varchar (255) NOT NULL,
  user_name varchar(50) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  password varchar(255) NOT NULL,
  retry_count integer,
  disabled boolean not null default false,
  last_access_at bigint,
  company_name varchar(100),
  created_at bigint,
  updated_at bigint,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE roles (
    id bigint NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	is_active bool NULL,
    is_default bool NULL,
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE user_roles (
    id bigint NOT NULL AUTO_INCREMENT,
	user_id varchar (255) NOT NULL,
	role_id bigint NOT NULL,
	primary key (id),
	constraint fk_user_roles_user foreign key (user_id) references users(id),
    constraint fk_user_roles_role foreign key (role_id) references roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE permissions (
    id bigint NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	is_active bool NULL,
	is_default bool NULL,
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE role_permissions (
    id bigint NOT NULL AUTO_INCREMENT,
	role_id bigint NOT NULL,
	permission_id bigint NOT NULL,
	primary key (id),
	constraint fk_role_permissions_role foreign key (role_id) references roles(id),
	constraint fk_role_permissions_permission foreign key (permission_id) references permissions(id)
);


CREATE TABLE oauth_access_token (
  id varchar(255) not null,
  authentication LONGBLOB,
  auth_id varchar(255),
  client_id varchar(255),
  refresh_token LONGBLOB,
  token LONGBLOB,
  user_name varchar(255),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE oauth_client_details (
  id varchar (255) NOT NULL,
  access_token_validity integer,
  additional_info varchar(255),
  authorities varchar(255),
  auth_grant_types varchar(255),
  client_id varchar(255),
  client_secret varchar(255),
  is_auto_approve bit,
  refresh_token_validity integer,
  registered_redirect_uris varchar(255),
  resource_ids varchar(255),
  scope varchar(255),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE oauth_code (
  id varchar(255) not null,
  authentication LONGBLOB,
  code varchar(255),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE oauth_refresh_token (
  id varchar(255) not null,
  authentication LONGBLOB,
  token LONGBLOB,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;