package DAO.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntityLiaisoncomponentfeedbackPK implements Serializable {
    private Integer liaisonFeedbackId;
    private Short componentVersion;
    private Integer componentId;

    public EntityLiaisoncomponentfeedbackPK() {
    }

    @Column(name = "liaison_feedback_id", nullable = false)
    public Integer getLiaisonFeedbackId() {
        return liaisonFeedbackId;
    }

    public void setLiaisonFeedbackId(Integer liaisonFeedbackId) {
        this.liaisonFeedbackId = liaisonFeedbackId;
    }

    @Column(name = "component_version", nullable = false)
    public Short getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(Short componentVersion) {
        this.componentVersion = componentVersion;
    }

    @Column(name = "component_id", nullable = false)
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLiaisoncomponentfeedbackPK that = (EntityLiaisoncomponentfeedbackPK) o;
        return Objects.equals(liaisonFeedbackId, that.liaisonFeedbackId) &&
                Objects.equals(componentVersion, that.componentVersion) &&
                Objects.equals(componentId, that.componentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(liaisonFeedbackId, componentVersion, componentId);
    }
}
