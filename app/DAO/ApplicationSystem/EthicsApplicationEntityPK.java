package DAO.ApplicationSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EthicsApplicationEntityPK implements Serializable {
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;

    @Column(name = "application_type")
    @Id
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Column(name = "application_year")
    @Id
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Column(name = "application_department")
    @Id
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Column(name = "application_number")
    @Id
    public short getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(short applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EthicsApplicationEntityPK that = (EthicsApplicationEntityPK) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationType, applicationYear, applicationDepartment, applicationNumber);
    }
}
