package DAO.ApplicationSystem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "componentversion", schema = "rech_system", catalog = "")
@IdClass(ComponentversionEntityPK.class)
public class ComponentversionEntity {
    private byte version;
    private Byte isSubmitted;
    private Timestamp dateSubmitted;
    private Timestamp dateLastEdited;
    private String responseType;
    private String textValue;
    private Byte boolValue;
    private String documentName;
    private String documentDescription;
    private byte[] documentBlob;
    private short sectionId;
    private short componentId;
    private String applicationType;
    private int applicationYear;
    private String applicationDepartment;
    private short applicationNumber;

    @Id
    @Column(name = "version")
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
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
    @Column(name = "document_blob")
    public byte[] getDocumentBlob() {
        return documentBlob;
    }

    public void setDocumentBlob(byte[] documentBlob) {
        this.documentBlob = documentBlob;
    }

    @Id
    @Column(name = "section_id")
    public short getSectionId() {
        return sectionId;
    }

    public void setSectionId(short sectionId) {
        this.sectionId = sectionId;
    }

    @Id
    @Column(name = "component_id")
    public short getComponentId() {
        return componentId;
    }

    public void setComponentId(short componentId) {
        this.componentId = componentId;
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
    @Column(name = "application_year")
    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    @Id
    @Column(name = "application_department")
    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    @Id
    @Column(name = "application_number")
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
        ComponentversionEntity that = (ComponentversionEntity) o;
        return version == that.version &&
                sectionId == that.sectionId &&
                componentId == that.componentId &&
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
                Arrays.equals(documentBlob, that.documentBlob) &&
                Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationDepartment, that.applicationDepartment);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(version, isSubmitted, dateSubmitted, dateLastEdited, responseType, textValue, boolValue, documentName, documentDescription, sectionId, componentId, applicationType, applicationYear, applicationDepartment, applicationNumber);
        result = 31 * result + Arrays.hashCode(documentBlob);
        return result;
    }
}
