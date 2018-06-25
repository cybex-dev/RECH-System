package DAO.UserSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PersonEntityPK implements Serializable {
    private String userEmail;
    private String facultyFacultyName;
    private String departmentDepartmentName;
    private String departmentFacultyName;

    @Column(name = "user_email")
    @Id
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "Faculty_faculty_name")
    @Id
    public String getFacultyFacultyName() {
        return facultyFacultyName;
    }

    public void setFacultyFacultyName(String facultyFacultyName) {
        this.facultyFacultyName = facultyFacultyName;
    }

    @Column(name = "Department_department_name")
    @Id
    public String getDepartmentDepartmentName() {
        return departmentDepartmentName;
    }

    public void setDepartmentDepartmentName(String departmentDepartmentName) {
        this.departmentDepartmentName = departmentDepartmentName;
    }

    @Column(name = "Department_faculty_name")
    @Id
    public String getDepartmentFacultyName() {
        return departmentFacultyName;
    }

    public void setDepartmentFacultyName(String departmentFacultyName) {
        this.departmentFacultyName = departmentFacultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntityPK that = (PersonEntityPK) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(facultyFacultyName, that.facultyFacultyName) &&
                Objects.equals(departmentDepartmentName, that.departmentDepartmentName) &&
                Objects.equals(departmentFacultyName, that.departmentFacultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userEmail, facultyFacultyName, departmentDepartmentName, departmentFacultyName);
    }
}
