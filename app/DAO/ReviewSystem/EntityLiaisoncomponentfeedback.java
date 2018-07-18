package DAO.ReviewSystem;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "liaisoncomponentfeedback", schema = "rech_system", catalog = "")
@IdClass(EntityLiaisoncomponentfeedbackPK.class)
public class EntityLiaisoncomponentfeedback {
    private Integer liaisonFeedbackId;
    private Short componentVersion;
    private Integer componentId;
    private String componentFeedback;

    @Id
    @Column(name = "liaison_feedback_id", nullable = false)
    public Integer getLiaisonFeedbackId() {
        return liaisonFeedbackId;
    }

    public void setLiaisonFeedbackId(Integer liaisonFeedbackId) {
        this.liaisonFeedbackId = liaisonFeedbackId;
    }

    @Id
    @Column(name = "component_version", nullable = false)
    public Short getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(Short componentVersion) {
        this.componentVersion = componentVersion;
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
    @Column(name = "component_feedback", nullable = true, length = 255)
    public String getComponentFeedback() {
        return componentFeedback;
    }

    public void setComponentFeedback(String componentFeedback) {
        this.componentFeedback = componentFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisoncomponentfeedback that = (EntityLiaisoncomponentfeedback) o;
        return Objects.equals(liaisonFeedbackId, that.liaisonFeedbackId) &&
                Objects.equals(componentVersion, that.componentVersion) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(componentFeedback, that.componentFeedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(liaisonFeedbackId, componentVersion, componentId, componentFeedback);
    }
}
