package dao.NMU;

import exceptions.InvalidFieldException;
import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "Department", schema = "rech_system")
@IdClass(EntityDepartmentPK.class)
public class EntityDepartment {
    public static class DepartmentContainer {
        public String dept, faculty;

        public static DepartmentContainer create(String dept, String faculty) {
            DepartmentContainer d = new DepartmentContainer();
            d.dept = dept;
            d.faculty = faculty;
            return d;
        }
    }

    private String departmentName;
    private String facultyFacultyName;

    public static Finder<dao.NMU.EntityDepartmentPK, dao.NMU.EntityDepartment> find = new Finder<>(dao.NMU.EntityDepartment.class);

    public static List<DepartmentContainer> getAllDepartmentNames() {
        return find.all().stream().map(entityDepartment -> DepartmentContainer.create(entityDepartment.departmentName, entityDepartment.facultyFacultyName)).collect(Collectors.toList());
    }

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
