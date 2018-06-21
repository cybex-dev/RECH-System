package models.NMU;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Embeddable
@Table(name = "department")
public class Department implements Serializable {
    @Id
    @Column(name = "department_name")
    private String department_name;

    @ManyToOne
    @ForeignKey(name = "faculty_name")
    private Faculty faculty_name;


}
