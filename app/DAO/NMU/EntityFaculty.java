package DAO.NMU;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "faculty", schema = "rech_system", catalog = "")
public class EntityFaculty extends Model {
    private String facultyName;
    private String facultyInfo;

    @Id
    @Column(name = "faculty_name")
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "faculty_info")
    public String getFacultyInfo() {
        return facultyInfo;
    }

    public void setFacultyInfo(String facultyInfo) {
        this.facultyInfo = facultyInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityFaculty that = (EntityFaculty) o;
        return Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(facultyInfo, that.facultyInfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(facultyName, facultyInfo);
    }
}
