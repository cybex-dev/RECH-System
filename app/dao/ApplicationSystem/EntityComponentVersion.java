package dao.ApplicationSystem;

import dao.ApplicationSystem.EntityComponentversion;
import dao.ApplicationSystem.EntityComponentversionPK;
import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ComponentVersion", schema = "rech_system")
@IdClass(EntityComponentVersionPK.class)
public class EntityComponentVersion {
    private Short version;
    private String componentId;
    private String applicationType;
    private Integer applicationYear;
    private Integer applicationNumber;
    private String departmentName;
    private String facultyName;
    private Byte isSubmitted;
    private Timestamp dateSubmitted;
    private Timestamp dateLastEdited;
    private String responseType;
    private String textValue;
    private Byte boolValue;
    private String documentName;
    private String documentDescription;
    private String documentLocationHash;

    public static Finder<EntityComponentversionPK, EntityComponentversion> find = new Finder<>(dao.ApplicationSystem.EntityComponentversion.class);

    @Id
    @Column(name = "version", nullable = false)
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Id
    @Column(name = "component_id", nullable = false, length = 50)
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @Id
    @Column(name = "application_type", nullable = false, length = 1)
    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Id
    @Column(name = "application_year", nullable = false)
    public Integer getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(Integer applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_number", nullable = false)
    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    @Id
    @Column(name = "department_name", nullable = false, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Id
    @Column(name = "faculty_name", nullable = false, length = 50)
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "is_submitted", nullable = true)
    public Byte getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Byte isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    @Basic
    @Column(name = "date_submitted", nullable = true)
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "date_last_edited", nullable = true)
    public Timestamp getDateLastEdited() {
        return dateLastEdited;
    }

    public void setDateLastEdited(Timestamp dateLastEdited) {
        this.dateLastEdited = dateLastEdited;
    }

    @Basic
    @Column(name = "response_type", nullable = true, length = 8)
    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Basic
    @Column(name = "text_value", nullable = true, length = 255)
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Basic
    @Column(name = "bool_value", nullable = true)
    public Byte getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Byte boolValue) {
        this.boolValue = boolValue;
    }

    @Basic
    @Column(name = "document_name", nullable = true, length = 100)
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Basic
    @Column(name = "document_description", nullable = true, length = 255)
    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    @Basic
    @Column(name = "document_location_hash", nullable = true, length = 255)
    public String getDocumentLocationHash() {
        return documentLocationHash;
    }

    public void setDocumentLocationHash(String documentLocationHash) {
        this.documentLocationHash = documentLocationHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityComponentVersion that = (EntityComponentVersion) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationYear, that.applicationYear) &&
                Objects.equals(applicationNumber, that.applicationNumber) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName) &&
                Objects.equals(isSubmitted, that.isSubmitted) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(dateLastEdited, that.dateLastEdited) &&
                Objects.equals(responseType, that.responseType) &&
                Objects.equals(textValue, that.textValue) &&
                Objects.equals(boolValue, that.boolValue) &&
                Objects.equals(documentName, that.documentName) &&
                Objects.equals(documentDescription, that.documentDescription) &&
                Objects.equals(documentLocationHash, that.documentLocationHash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId, applicationType, applicationYear, applicationNumber, departmentName, facultyName, isSubmitted, dateSubmitted, dateLastEdited, responseType, textValue, boolValue, documentName, documentDescription, documentLocationHash);
    }

    public static EntityComponentversion getLatestComponent(String componentId) {
        return find.all()
                .stream()
                .filter(entityComponentversion -> entityComponentversion.componentId.equals(componentId))
                .max((o1, o2) -> o1.version > o2.version ? -1 : (o1.version < o2.version ? 1 : 0))
                .orElseThrow(() -> new EntityNotFoundException("Cannot find any value for componentId: " + String.valueOf(componentId)));

    }


}
