package dao.ApplicationSystem;

import dao.NMU.EntityDepartment;
import dao.NMU.EntityFaculty;
import models.ApplicationSystem.EthicsApplication;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class EntityEthicsApplicationPK implements Serializable {
    private int applicationYear;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;

    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
        EntityEthicsApplicationPK that = (EntityEthicsApplicationPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType.toLowerCase(), that.applicationType.toLowerCase()) &&
                Objects.equals(departmentName.toLowerCase(), that.departmentName.toLowerCase()) &&
                Objects.equals(facultyName.toLowerCase(), that.facultyName.toLowerCase());
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }

    public static dao.ApplicationSystem.EntityEthicsApplicationPK fromString(String id) {

        List<String> strings = Arrays.asList(id.split("-"));
        EthicsApplication.ApplicationType type = EthicsApplication.ApplicationType.parse(strings.get(0).substring(0,1));
        int year = Integer.parseInt("20" + strings.get(0).substring(1));

        String faculty = EntityFaculty.getFacultyByShortName(strings.get(1));
        String department = EntityDepartment.findDepartmentByShortName(strings.get(2));
        int number = Integer.parseInt(strings.get(3));

        EntityEthicsApplicationPK pk = new EntityEthicsApplicationPK();
        pk.setApplicationNumber(number);
        pk.setApplicationType(type.name());
        pk.setApplicationYear(year);
        pk.setDepartmentName(department);
        pk.setFacultyName(faculty);
        return pk;
    }

    public String shortName(){
        StringBuilder builder = new StringBuilder();

        String dept = departmentName;
        if (departmentName.contains("(")) {
            int i = departmentName.indexOf("(");
            dept = departmentName.substring(0, i - 1);
        }

        String[] s1 = dept.split(" ");
        dept = "";
        if (s1.length == 1) {
            dept = s1[0].substring(0, 3);
        } else if(s1.length == 2) {
            dept = s1[0].substring(0, 1) + s1[1].substring(0, 1) + s1[1].substring(0, 1);
        } else {
            dept = s1[0].substring(0, 1) + s1[1].substring(0, 1) + s1[2].substring(0, 1);
        }

        DecimalFormat decimalFormat = new DecimalFormat("000");
        String number = decimalFormat.format(applicationNumber);

        String type = applicationType.substring(0,1);
        String year = String.valueOf(applicationYear).substring(2, 4);
        String faculty = facultyName.substring(0, 3);

        builder.append(type).append(year).append("-")
                .append(faculty).append("-")
                .append(dept).append("-")
                .append(number);
        return builder.toString().toUpperCase();
    }
}
