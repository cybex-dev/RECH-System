package dao.ApplicationSystem;

import models.ApplicationSystem.EthicsApplication;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Embeddable
public class EntityEthicsApplicationPK implements Serializable {
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;

    public EntityEthicsApplicationPK() {
    }

    public static EntityEthicsApplicationPK fromString(String user, String id) {

        String[] split = id.split("_");
        Integer number = Integer.parseInt(split[0]);
        Integer year = Integer.parseInt(split[1]);

        EthicsApplication.ApplicationType type = EthicsApplication.ApplicationType.parse(split[2]);
        String appType = type.toString();

        return EntityEthicsApplication.find.all().stream()
                .filter(entityEthicsApplication -> entityEthicsApplication.getApplicationYear().equals(year) &&
                        entityEthicsApplication.getApplicationNumber().equals(number) &&
                        entityEthicsApplication.getApplicationType().equals(appType))
                .map(entityEthicsApplication -> entityEthicsApplication.applicationPrimaryKey())
                .findFirst()
                .orElseThrow(null);

    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
        return Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationNumber, departmentName, facultyName);
    }

    @Override
    public String toString() {
        String builder = String.valueOf(applicationNumber) +
                "_" +
                applicationYear +
                "_" +
                applicationType;
        return builder;
    }
}
