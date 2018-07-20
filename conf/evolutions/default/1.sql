# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table rech_system.agendaitem (
  meeting_date                  datetime(6) not null,
  application_id                integer not null,
  resolution                    varchar(255),
  application_status            smallint
);

create table rech_system.component (
  component_id                  integer auto_increment not null,
  question_id                   varchar(255),
  application_id                integer not null,
  constraint pk_component primary key (component_id)
);

create table rech_system.componentversion (
  version                       smallint not null,
  component_id                  integer not null,
  is_submitted                  tinyint(1),
  date_submitted                datetime(6),
  date_last_edited              datetime(6),
  response_type                 varchar(8),
  text_value                    varchar(255),
  bool_value                    tinyint(1),
  document_name                 varchar(100),
  document_description          varchar(255),
  document_blob                 varbinary(255)
);

create table rech_system.department (
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null
);

create table rech_system.ethics_application (
  application_id                integer auto_increment not null,
  application_number            integer not null,
  application_type              varchar(1) not null,
  application_year              integer not null,
  department_name               varchar(50) not null,
  faculty_name                  varchar(50) not null,
  is_submitted                  tinyint(1),
  date_submitted                datetime(6)(45),
  date_approved                 datetime(6)(45),
  pi_id                         varchar(100) not null,
  pi_approved_date              datetime(6)(45),
  prp_id                        varchar(100) not null,
  prp_approved_date             datetime(6)(45),
  hod_id                        varchar(100),
  hod_approved_date             datetime(6)(45),
  rti_id                        varchar(100),
  rti_approved_date             datetime(6)(45),
  liaison_id                    varchar(100),
  internal_status               smallint,
  prp_approved_status           tinyint(1),
  hod_approved_status           tinyint(1),
  rti_approved_status           tinyint(1),
  constraint pk_ethics_application primary key (application_id)
);

create table rech_system.faculty (
  faculty_name                  varchar(50) not null,
  faculty_info                  varchar(255),
  constraint pk_faculty primary key (faculty_name)
);

create table rech_system.liaisoncomponentfeedback (
  liaison_feedback_id           integer not null,
  component_version             smallint not null,
  component_id                  integer not null,
  component_feedback            varchar(255)
);

create table rech_system.liaisonfeedback (
  liaison_feedback_id           integer auto_increment not null,
  feedback_date                 datetime(6) not null,
  application_assigned_date     datetime(6),
  liaison_email                 varchar(100) not null,
  application_id                integer not null,
  requires_edits                tinyint(1)(45),
  constraint pk_liaisonfeedback primary key (liaison_feedback_id)
);

create table rech_system.meeting (
  meeting_date                  datetime(6) not null,
  announcements                 varchar(255),
  constraint pk_meeting primary key (meeting_date)
);

create table rech_system.message (
  message_date                  datetime(6)(45) not null,
  user_email_sender             varchar(100) not null,
  user_email_receiver           varchar(100) not null,
  application_id                integer not null,
  message                       varchar(255)
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
  reviewer_feedback_id          integer not null,
  component_version             smallint not null,
  component_id                  integer not null,
  component_feedback            varchar(255)
);

create table rech_system.reviewerfeedback (
  reviewer_feedback_id          integer auto_increment not null,
  feedback_date                 datetime(6),
  application_assigned_date     datetime(6),
  reviewer_email                varchar(100) not null,
  application_id                integer not null,
  requires_edits                tinyint(1),
  satisfactory                  tinyint(1),
  constraint pk_reviewerfeedback primary key (reviewer_feedback_id)
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

