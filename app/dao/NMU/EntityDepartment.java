package dao.NMU;

import exceptions.InvalidFieldException;
import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "department", schema = "rech_system")
@IdClass(EntityDepartmentPK.class)
public class EntityDepartment {
    private String departmentName;
    private String facultyName;

    public static class DepartmentContainer {
        public String dept, faculty;

        public static DepartmentContainer create(String dept, String faculty) {
            DepartmentContainer d = new DepartmentContainer();
            d.dept = dept;
            d.faculty = faculty;
            return d;
        }
    }

    public static Finder<EntityDepartmentPK, dao.NMU.EntityDepartment> find = new Finder<>(dao.NMU.EntityDepartment.class);

    public static List<DepartmentContainer> getAllDepartmentNames() {
        return find.all().stream().map(entityDepartment -> DepartmentContainer.create(entityDepartment.departmentName, entityDepartment.facultyName)).collect(Collectors.toList());
    }

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

    public static String findFacultyByDepartment(String dept) throws InvalidFieldException {
        return find.all()
                .stream()
                .filter(entityDepartment -> entityDepartment.departmentName.equals(dept))
                .findFirst()
                .orElseThrow(() -> new InvalidFieldException("Department has no associated faculty"))
                .getFacultyName();
    }
}
