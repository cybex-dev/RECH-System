package models.UserSystem;

import models.NMU.Department;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "user_email")
    private String user_email;

    @Column(name = "user_password_hash")
    private String password_hash;

    @Column(name = "user_title")
    private String title;

    @Column(name = "user_firstname")
    private String firstname;

    @Column(name = "user_lastname")
    private String lastname;

    @Column(name = "user_gender")
    private boolean gender;

    @Column(name = "current_degree_level")
    private String degree_level;

    @Column(name = "contact_number_mobile")
    private String contact_mobile;

    @Column(name = "person_type")
    private PersonType personType;

    @Column(name = "contact_number_telephone")
    private String contact_telephone;

    @Column(name = "office_address")
    private OfficeAddress officeAddress;

    @OneToOne
    @Column(name = "")
    @ForeignKey(name = "department_name")
    private Department department;
}
