package dao.ApplicationSystem;

import io.ebean.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "componentversion", schema = "rech_system")
@IdClass(EntityComponentVersionPK.class)
public class EntityComponentVersion {
    private short version;
    private Byte isSubmitted;
    private Timestamp dateSubmitted;
    private Timestamp dateLastEdited;
    private String responseType;
    private String textValue;
    private Byte boolValue;
    private String documentName;
    private String documentDescription;
    private String documentLocationHash;
    private String componentId;
    private int applicationYear;
    private int applicationNumber;
    private String applicationType;
    private String departmentName;
    private String facultyName;

    public static Finder<EntityComponentVersionPK, EntityComponentVersion> find = new Finder<>(dao.ApplicationSystem.EntityComponentVersion.class);

    @Id
    @Column(name = "version")
    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    @Basic
    @Column(name = "is_submitted")
    public Byte getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Byte isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    @Basic
    @Column(name = "date_submitted")
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "date_last_edited")
    public Timestamp getDateLastEdited() {
        return dateLastEdited;
    }

    public void setDateLastEdited(Timestamp dateLastEdited) {
        this.dateLastEdited = dateLastEdited;
    }

    @Basic
    @Column(name = "response_type")
    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Basic
    @Column(name = "text_value")
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Basic
    @Column(name = "bool_value")
    public Byte getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Byte boolValue) {
        this.boolValue = boolValue;
    }

    @Basic
    @Column(name = "document_name")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Basic
    @Column(name = "document_description")
    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    @Basic
    @Column(name = "document_location_hash")
    public String getDocumentLocationHash() {
        return documentLocationHash;
    }

    public void setDocumentLocationHash(String documentLocationHash) {
        this.documentLocationHash = documentLocationHash;
    }

    @Id
    @Column(name = "component_id")
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
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
        EntityComponentVersion that = (EntityComponentVersion) o;
        return version == that.version &&
                applicationYear == that.applicationYear &&
                applicationNumber == that.applicationNumber &&
                Objects.equals(isSubmitted, that.isSubmitted) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(dateLastEdited, that.dateLastEdited) &&
                Objects.equals(responseType, that.responseType) &&
                Objects.equals(textValue, that.textValue) &&
                Objects.equals(boolValue, that.boolValue) &&
                Objects.equals(documentName, that.documentName) &&
                Objects.equals(documentDescription, that.documentDescription) &&
                Objects.equals(documentLocationHash, that.documentLocationHash) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, isSubmitted, dateSubmitted, dateLastEdited, responseType, textValue, boolValue, documentName, documentDescription, documentLocationHash, componentId, applicationYear, applicationNumber, applicationType, departmentName, facultyName);
    }


    public static EntityComponentVersion getLatestComponent(String componentId) {
        return find.all()
                .stream()
                .filter(entityComponentversion -> entityComponentversion.componentId.equals(componentId))
                .max((o1, o2) -> o1.version > o2.version ? -1 : (o1.version < o2.version ? 1 : 0))
                .orElseThrow(() -> new EntityNotFoundException("Cannot find any value for componentId: " + String.valueOf(componentId)));

    }
}
