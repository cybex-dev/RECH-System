# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table rech_system.agendaitem (
  meeting_date                  datetime(6) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  resolution                    varchar(255),
  application_status            smallint
);

create table rech_system.component (
  component_id                  varchar(50) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  question                      varchar(255)
);

create table rech_system.componentversion (
  version                       smallint not null,
  component_id                  varchar(50) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  is_submitted                  tinyint(1),
  date_submitted                datetime(6),
  date_last_edited              datetime(6),
  response_type                 varchar(8),
  text_value                    varchar(255),
  bool_value                    tinyint(1),
  document_name                 varchar(100),
  document_description          varchar(255),
  document_location_hash        varchar(255)
);

create table rech_system.department (
  department_name               varchar(50) not null,
  faculty_faculty_name          varchar(255),
  faculty_faculty_name          varchar(50) not null
);

create table rech_system.ethics_application (
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  date_submitted                datetime(6),
  date_approved                 datetime(6),
  pi_id                         varchar(100) not null,
  pi_approved_date              datetime(6),
  prp_id                        varchar(100) not null,
  prp_approved_date             datetime(6),
  hod_id                        varchar(100),
  hod_pre_approved_date         datetime(6),
  hod_post_approved_date        datetime(6),
  hod_application_review_approved tinyint(1),
  hod_final_application_approval tinyint(1),
  rti_id                        varchar(100),
  rti_pre_approved_date         datetime(6),
  rti_post_approved_date        datetime(6),
  rti_application_review_approved tinyint(1),
  rti_final_application_approval tinyint(1),
  internal_status               smallint,
  liaison_id                    varchar(100),
  liaison_assigned_date         datetime(6),
  application_level             smallint
);

create table rech_system.faculty (
  faculty_name                  varchar(50) not null,
  faculty_info                  varchar(255),
  constraint pk_faculty primary key (faculty_name)
);

create table rech_system.liaisoncomponentfeedback (
  version                       smallint not null,
  component_id                  varchar(50) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  liaisonfeedback_feedback_date datetime(6) not null,
  liaisonfeedback_ethics_application_application_type varchar(1) not null,
  liaisonfeedback_ethics_application_application_year integer not null,
  liaisonfeedback_ethics_application_application_number integer not null,
  liaisonfeedback_ethics_application_department_name varchar(50) not null,
  liaisonfeedback_ethics_application_faculty_name varchar(50) not null,
  component_feedback            varchar(255)
);

create table rech_system.liaisonfeedback (
  feedback_date                 datetime(6) not null,
  ethics_application_application_type varchar(1) not null,
  ethics_application_application_year integer not null,
  ethics_application_application_number integer not null,
  ethics_application_department_name varchar(50) not null,
  ethics_application_faculty_name varchar(50) not null,
  liaison_email                 varchar(100) not null,
  requires_edits                tinyint(1)
);

create table rech_system.meeting (
  meeting_date                  datetime(6) not null,
  announcements                 varchar(45),
  constraint pk_meeting primary key (meeting_date)
);

create table rech_system.message (
  message_date                  datetime(6) not null,
  reciever_email                varchar(100) not null,
  sender_email                  varchar(100) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  message                       varchar(255),
  constraint pk_message primary key (message_date)
);

create table rech_system.person (
  user_email                    varchar(100) not null,
  user_password_hash            varchar(50) not null,
  user_firstname                varchar(50),
  user_lastname                 varchar(50),
  user_gender                   varchar(1),
  current_degree_level          varchar(20),
  contact_number_mobile         varchar(15),
  person_type                   varchar(10),
  contact_office_telephone      varchar(15),
  office_address                varchar(40),
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  constraint pk_person primary key (user_email)
);

create table rech_system.reviewercomponentfeedback (
  version                       smallint not null,
  component_id                  varchar(50) not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  application_number            integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  reviewerfeedback_application_assigned_date datetime(6) not null,
  reviewerfeedback_ethics_application_application_type varchar(1) not null,
  reviewerfeedback_ethics_application_application_year integer not null,
  reviewerfeedback_ethics_application_application_number integer not null,
  reviewerfeedback_ethics_application_department_name varchar(50) not null,
  reviewerfeedback_ethics_application_faculty_name varchar(50) not null,
  component_feedback            varchar(255)
);

create table rech_system.reviewerfeedback (
  application_assigned_date     datetime(6) not null,
  ethics_application_application_type varchar(1) not null,
  ethics_application_application_year integer not null,
  ethics_application_application_number integer not null,
  ethics_application_department_name varchar(50) not null,
  ethics_application_faculty_name varchar(50) not null,
  reviewer_email                varchar(100) not null,
  feedback_date                 datetime(6),
  requires_edits                tinyint(1)
);


# --- !Downs

drop table if exists rech_system.agendaitem;

drop table if exists rech_system.component;

drop table if exists rech_system.componentversion;

drop table if exists rech_system.department;

drop table if exists rech_system.ethics_application;

drop table if exists rech_system.faculty;

drop table if exists rech_system.liaisoncomponentfeedback;

drop table if exists rech_system.liaisonfeedback;

drop table if exists rech_system.meeting;

drop table if exists rech_system.message;

drop table if exists rech_system.person;

drop table if exists rech_system.reviewercomponentfeedback;

drop table if exists rech_system.reviewerfeedback;

