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

    public static EntityDepartment FromShortName(String dept_fac) {
        List<EntityDepartment> collect = find.all().stream()
                .filter(entityDepartment -> {
                    String name = entityDepartment.shortName();
                    return name.equals(dept_fac);
                }).collect(Collectors.toList());
        if (collect.size() == 0)
            return null;
        else if (collect.size() > 1)
            try {
                throw new UnknownDataException("Department-Faculty shortname has duplicates:\n" + collect.stream().map(EntityDepartment::shortName).reduce((s, s2) -> s.concat("-").concat(s2)).toString());
            } catch (UnknownDataException e) {
                e.printStackTrace();
            }
        return collect.get(0);
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

    public String shortName() {
        EntityFaculty faculty = EntityFaculty.getFacultyByName(facultyName);
        String dept = Arrays.stream(departmentName.split(" "))
                .map(s -> {
                    if (s.length() > 3) {
                        return s.substring(0, 3);
                    } else {
                        return "";
                    }
                })
                .reduce((s, s2) -> {
                    if (s2.isEmpty()) {
                        return s;
                    } else {
                        return s.concat("-").concat(s2);
                    }
                })
                .orElse("Any");
        return dept.concat("_").concat(faculty.shortName());
    }
}
