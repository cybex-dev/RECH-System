package DAO.ApplicationSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "componentversion", schema = "rech_system", catalog = "")
@IdClass(EntityComponentversionPK.class)
public class EntityComponentversion extends Model {
    private Short version;
    private Integer componentId;
    private Boolean isSubmitted;
    private Timestamp dateSubmitted;
    private Timestamp dateLastEdited;
    private String responseType;
    private String textValue;
    private Boolean boolValue;
    private String documentName;
    private String documentDescription;
    private byte[] documentBlob;

    public static Finder<EntityComponentversionPK, DAO.ApplicationSystem.EntityComponentversion> find = new Finder<>(DAO.ApplicationSystem.EntityComponentversion.class);

    @Id
    @Column(name = "version", nullable = false)
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Id
    @Column(name = "component_id", nullable = false)
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @Basic
    @Column(name = "is_submitted", nullable = true)
    public Boolean getSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(Boolean submitted) {
        isSubmitted = submitted;
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
    @Column(name = "text_value", nullable = true, length = -1)
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Basic
    @Column(name = "bool_value", nullable = true)
    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
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
    @Column(name = "document_blob", nullable = true)
    public byte[] getDocumentBlob() {
        return documentBlob;
    }

    public void setDocumentBlob(byte[] documentBlob) {
        this.documentBlob = documentBlob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityComponentversion that = (EntityComponentversion) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(isSubmitted, that.isSubmitted) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(dateLastEdited, that.dateLastEdited) &&
                Objects.equals(responseType, that.responseType) &&
                Objects.equals(textValue, that.textValue) &&
                Objects.equals(boolValue, that.boolValue) &&
                Objects.equals(documentName, that.documentName) &&
                Objects.equals(documentDescription, that.documentDescription) &&
                Arrays.equals(documentBlob, that.documentBlob);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(version, componentId, isSubmitted, dateSubmitted, dateLastEdited, responseType, textValue, boolValue, documentName, documentDescription);
        result = 31 * result + Arrays.hashCode(documentBlob);
        return result;
    }

    public static DAO.ApplicationSystem.EntityComponentversion getLatestComponent(Integer componentId) {
        return find.all()
                .stream()
                .filter(entityComponentversion -> entityComponentversion.componentId.equals(componentId))
                .max((o1, o2) -> o1.version > o2.version ? -1 : (o1.version < o2.version ? 1 : 0))
                .orElseThrow(() -> new EntityNotFoundException("Cannot find any value for componentId: " + String.valueOf(componentId)));
    }
}
