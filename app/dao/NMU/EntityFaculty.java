package dao.NMU;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Faculty", schema = "rech_system")
public class EntityFaculty {
    private String facultyName;
    private String facultyInfo;

    public static Finder<String, dao.NMU.EntityFaculty> find = new Finder<>(dao.NMU.EntityFaculty.class);

    @Id
    @Column(name = "faculty_name", nullable = false, length = 50)
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "faculty_info", nullable = true, length = -1)
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

    public static dao.NMU.EntityFaculty getFacultyByName(String facultyName){
        return find.byId(facultyName);
    }

}
