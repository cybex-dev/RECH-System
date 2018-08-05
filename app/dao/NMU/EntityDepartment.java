package dao.NMU;

import exceptions.InvalidFieldException;
import io.ebean.Finder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Department", schema = "rech_system", catalog = "")
@IdClass(EntityDepartmentPK.class)
public class EntityDepartment {
    private String departmentName;
    private String facultyFacultyName;

    public static Finder<dao.NMU.EntityDepartmentPK, dao.NMU.EntityDepartment> find = new Finder<>(dao.NMU.EntityDepartment.class);

    @Id
    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "Faculty_faculty_name", nullable = false, length = 50)
    public String getFacultyFacultyName() {
        return facultyFacultyName;
    }

    public void setFacultyFacultyName(String facultyFacultyName) {
        this.facultyFacultyName = facultyFacultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDepartment that = (EntityDepartment) o;
        return Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyFacultyName, that.facultyFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentName, facultyFacultyName);
    }


    public static String findFacultyByDepartment(String dept) throws InvalidFieldException {
        return find.all()
                .stream()
                .filter(entityDepartment -> entityDepartment.departmentName.equals(dept))
                .findFirst()
                .orElseThrow(() -> new InvalidFieldException("Department has no associated faculty"))
                .getFacultyFacultyName();
    }


}
