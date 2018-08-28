# --- !Ups

create table rech_system.agendaitem (
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  meeting_date                  datetime(6) not null,
  resolution                    varchar(255),
  application_status            smallint
);

create table rech_system.component (
  component_id                  varchar(255) not null,
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  question                      varchar(255)
);

create table rech_system.componentversion (
  version                       smallint not null,
  component_id                  varchar(255) not null,
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  is_submitted                  tinyint(1),
  date_submitted                datetime(6),
  date_last_edited              datetime(6),
  response_type                 varchar(255),
  text_value                    varchar(255),
  bool_value                    tinyint(1),
  document_name                 varchar(255),
  document_description          varchar(255),
  document_location_hash        varchar(255)
);

create table rech_system.department (
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null
);

create table rech_system.ethics_application (
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  date_submitted                datetime(6),
  application_revision          integer,
  date_approved                 datetime(6),
  pi_approved_date              datetime(6),
  prp_approved_date             datetime(6),
  hod_pre_approved_date         datetime(6),
  hod_post_approved_date        datetime(6),
  rti_pre_approved_date         datetime(6),
  rti_post_approved_date        datetime(6),
  internal_status               smallint,
  liaison_assigned_date         datetime(6),
  hod_application_review_approved tinyint(1),
  hod_final_application_approval tinyint(1),
  rti_application_review_approved tinyint(1),
  rti_final_application_approval tinyint(1),
  application_level             smallint,
  pi_id                         varchar(255),
  prp_id                        varchar(255),
  rti_id                        varchar(255),
  hod_id                        varchar(255),
  liaison_id                    varchar(255)
);

create table rech_system.faculty (
  faculty_name                  varchar(255) not null,
  faculty_info                  varchar(255),
  constraint pk_faculty primary key (faculty_name)
);

create table rech_system.liaisoncomponentfeedback (
  liaison_email                 varchar(255) not null,
  version                       smallint not null,
  component_id                  varchar(255) not null,
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  change_satisfactory           tinyint(1),
  feedback_date                 datetime(6)
);

create table rech_system.meeting (
  meeting_date                  datetime(6) not null,
  announcements                 varchar(255),
  constraint pk_meeting primary key (meeting_date)
);

create table rech_system.message (
  message_date                  datetime(6) not null,
  message                       varchar(255),
  sender_email                  varchar(255),
  receiver_email1               varchar(255),
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255),
  department_name               varchar(255),
  faculty_name                  varchar(255),
  constraint pk_message primary key (message_date)
);

create table rech_system.person (
  user_email                    varchar(255) not null,
  user_password_hash            varchar(255),
  user_firstname                varchar(255),
  user_lastname                 varchar(255),
  user_gender                   varchar(255),
  user_title                    varchar(255),
  current_degree_level          varchar(255),
  contact_number_mobile         varchar(255),
  person_type                   varchar(255),
  contact_office_telephone      varchar(255),
  office_address                varchar(255),
  department_name               varchar(255),
  faculty_name                  varchar(255),
  constraint pk_person primary key (user_email)
);

create table rech_system.reviewerapplications (
  reviewer_email                varchar(255) not null,
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  date_assigned                 datetime(6)
);

create table rech_system.reviewercomponentfeedback (
  reviewer_email                varchar(255) not null,
  version                       smallint not null,
  component_id                  varchar(255) not null,
  application_year              integer not null,
  application_number            integer not null,
  application_type              varchar(255) not null,
  department_name               varchar(255) not null,
  faculty_name                  varchar(255) not null,
  component_feedback            varchar(255),
  feedback_date                 datetime(6)
);


# --- !Downs

drop table if exists rech_system.agendaitem;

drop table if exists rech_system.component;

drop table if exists rech_system.componentversion;

drop table if exists rech_system.department;

drop table if exists rech_system.ethics_application;

drop table if exists rech_system.faculty;

drop table if exists rech_system.liaisoncomponentfeedback;

drop table if exists rech_system.meeting;

drop table if exists rech_system.message;

drop table if exists rech_system.person;

drop table if exists rech_system.reviewerapplications;

drop table if exists rech_system.reviewercomponentfeedback;

