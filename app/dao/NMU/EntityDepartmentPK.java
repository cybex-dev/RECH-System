package dao.NMU;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntityDepartmentPK implements Serializable {
    private String departmentName;
    private String facultyFacultyName;

    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
        EntityDepartmentPK that = (EntityDepartmentPK) o;
        return Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyFacultyName, that.facultyFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentName, facultyFacultyName);
    }
}
