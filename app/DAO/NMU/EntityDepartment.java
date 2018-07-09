package DAO.NMU;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department", schema = "rech_system", catalog = "")
@IdClass(EntityDepartmentPK.class)
public class EntityDepartment extends Model {
    private String departmentName;
    private String facultyName;

    @Id
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDepartment that = (EntityDepartment) o;
        return Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentName, facultyName);
    }
}
