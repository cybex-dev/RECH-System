# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ethics_application (
  app_id                        integer auto_increment not null,
  is_approved                   tinyint(1) default 0 not null,
  constraint pk_ethics_application primary key (app_id)
);

create table faculty (
  faculty_name                  varchar(255) not null,
  faculty_info                  varchar(255),
  constraint pk_faculty primary key (faculty_name)
);

create table message (
  receiver_user_email           varchar(255) not null,
  sender_user_email             varchar(255) not null,
  ethics_application_app_id     integer not null,
  message_date                  datetime(6),
  message                       varchar(255)
);

create index ix_message_receiver_user_email on message (receiver_user_email);
alter table message add constraint fk_message_receiver_user_email foreign key (receiver_user_email) references null (user_email) on delete restrict on update restrict;

create index ix_message_sender_user_email on message (sender_user_email);
alter table message add constraint fk_message_sender_user_email foreign key (sender_user_email) references null (user_email) on delete restrict on update restrict;

create index ix_message_ethics_application_app_id on message (ethics_application_app_id);
alter table message add constraint fk_message_ethics_application_app_id foreign key (ethics_application_app_id) references ethics_application (app_id) on delete restrict on update restrict;


# --- !Downs

alter table message drop foreign key fk_message_receiver_user_email;
drop index ix_message_receiver_user_email on message;

alter table message drop foreign key fk_message_sender_user_email;
drop index ix_message_sender_user_email on message;

alter table message drop foreign key fk_message_ethics_application_app_id;
drop index ix_message_ethics_application_app_id on message;

drop table if exists ethics_application;

drop table if exists faculty;

drop table if exists message;

