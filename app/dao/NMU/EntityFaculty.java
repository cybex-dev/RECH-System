package dao.NMU;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "faculty", schema = "rech_system")
public class EntityFaculty extends Model {
    private String facultyName;
    private String facultyInfo;


    public static Finder<String, dao.NMU.EntityFaculty> find = new Finder<>(dao.NMU.EntityFaculty.class);

    public static List<String> getAllFacultyNames() {
        return find.all().stream().map(dao.NMU.EntityFaculty::getFacultyName).collect(Collectors.toList());
    }


    public static dao.NMU.EntityFaculty getFacultyByName(String facultyName){
        return find.byId(facultyName);
    }

    public static String getFacultyByShortName(String shortname) {
        return find.all().stream()
                .filter(entityFaculty -> entityFaculty.facultyName.substring(0,3).toUpperCase().equals(shortname))
                .map(EntityFaculty::getFacultyName)
                .findFirst()
                .orElse("???");
    }

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

    public String shortName() {
        return Arrays.stream(facultyName.split(" ")).map(s -> s.substring(0, 3)).reduce((s, s2) -> s.concat("-").concat(s2)).orElse("Any") ;
    }
}
