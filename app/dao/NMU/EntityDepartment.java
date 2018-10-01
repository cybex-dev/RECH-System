package dao.NMU;

import exceptions.InvalidFieldException;
import exceptions.UnknownDataException;
import io.ebean.Finder;
import io.ebean.Model;

import javax.lang.model.UnknownEntityException;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Entity
@Table(name = "department", schema = "rech_system")
@IdClass(EntityDepartmentPK.class)
public class EntityDepartment extends Model {
    private String departmentName;
    private String facultyName;

    public static EntityDepartment findDepartmentByName(String departmentName) {
        return find.all().stream().filter(entityDepartment -> entityDepartment.departmentName.equals(departmentName)).findFirst().orElse(null);
    }

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

    // Cleanup ()
    // Check number of words 0, 1, 2, etc
    // If 1 word, get subscrt 3
    // if 2 words, get first char, duplicate second char
    // if 3 + words, get first 3 char


    public static String findDepartmentByShortName(String shortname) {

        return find.all().stream()
                .filter(department -> {
                    // Get department name, breaking ()
                    String dept = "";
                    if (department.departmentName.contains("(")) {
                        int i = department.departmentName.indexOf("(");
                        dept = department.departmentName.substring(0, i - 1);
                    } else {
                        dept = department.departmentName;
                    }

                    // Check # words
                    String[] s1 = dept.split(" ");

                    if (s1.length == 1){
                        dept = s1[0].substring(0, 3);
                    } else if (s1.length == 2) {
                        dept = s1[0].substring(0, 1) + s1[1].substring(0, 1) + s1[1].substring(0, 1);
                    } else if (s1.length > 2) {
                        dept = s1[0].substring(0, 1) + s1[1].substring(0, 1) + s1[2].substring(0, 1);
                    }

                    return (shortname.equals(dept));

                })
                .map(EntityDepartment::getDepartmentName)
                .findFirst()
                .orElse("???");
    }
}
