package dao.ApplicationSystem;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "component", schema = "rech_system")
@IdClass(EntityComponentPK.class)
public class EntityComponent {
    private String componentId;
    private String question;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;


    public static Finder<dao.ApplicationSystem.EntityComponentPK, dao.ApplicationSystem.EntityComponent> find = new Finder<>(dao.ApplicationSystem.EntityComponent.class);

    @Id
    @Column(name = "component_id")
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Id
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_number")
    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Id
    @Column(name = "application_type")
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
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
        EntityComponent that = (EntityComponent) o;
        return applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(question, that.question) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(componentId, question, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }


    public static List<dao.ApplicationSystem.EntityComponent> getAllApplicationCompontents(dao.ApplicationSystem.EntityEthicsApplicationPK applicationId) {
        return find.all()
                .stream()
                .filter(entityComponent -> entityComponent.isComponent(applicationId))
                .collect(Collectors.toList());
    }

    public boolean isComponent(dao.ApplicationSystem.EntityEthicsApplicationPK id){
        return this.applicationNumber == id.getApplicationNumber() &&
                this.applicationType.equals(id.getApplicationType()) &&
                this.applicationYear == id.getApplicationYear() &&
                this.facultyName.equals(id.getFacultyName()) &&
                this.departmentName.equals(id.getDepartmentName());
    }

    public void setApplicationId(EntityEthicsApplicationPK applicationId) {
        this.applicationYear = applicationId.getApplicationYear();
        this.applicationType = applicationId.getApplicationType();
        this.departmentName = applicationId.getDepartmentName();
        this.facultyName = applicationId.getFacultyName();
        this.applicationNumber = applicationId.getApplicationNumber();
    }
}
