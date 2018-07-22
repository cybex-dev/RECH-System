package dao.NMU;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "faculty", schema = "rech_system")
public class EntityFaculty extends Model {

    @Id
    @Column(name = "faculty_name", nullable = false, length = 50)
    private String facultyName;

    @Basic
    @Column(name = "faculty_info", nullable = true, length = -1)
    private String facultyInfo;

    public static Finder<String, EntityFaculty> find = new Finder<>(EntityFaculty.class);

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
    public static EntityFaculty getFacultyByName(String facultyName){
        return find.byId(facultyName);
    }
}
