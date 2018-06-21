package models.NMU;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @Column(name = "faculty_name")
    private String faculty_name;

    @Column(name = "faculty_info")
    private String faculty_information;

}
