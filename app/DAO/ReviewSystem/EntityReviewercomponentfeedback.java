package DAO.ReviewSystem;

import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reviewercomponentfeedback", schema = "rech_system", catalog = "")
@IdClass(EntityReviewercomponentfeedbackPK.class)
public class EntityReviewercomponentfeedback extends Model {
    private Integer reviewerFeedbackId;
    private Short componentVersion;
    private Integer componentId;
    private String componentFeedback;

    @Id
    @Column(name = "reviewer_feedback_id")
    public Integer getReviewerFeedbackId() {
        return reviewerFeedbackId;
    }

    public void setReviewerFeedbackId(Integer reviewerFeedbackId) {
        this.reviewerFeedbackId = reviewerFeedbackId;
    }

    @Id
    @Column(name = "component_version")
    public Short getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(Short componentVersion) {
        this.componentVersion = componentVersion;
    }

    @Id
    @Column(name = "component_id")
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @Basic
    @Column(name = "component_feedback")
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
        EntityReviewercomponentfeedback that = (EntityReviewercomponentfeedback) o;
        return Objects.equals(reviewerFeedbackId, that.reviewerFeedbackId) &&
                Objects.equals(componentVersion, that.componentVersion) &&
                Objects.equals(componentId, that.componentId) &&
                Objects.equals(componentFeedback, that.componentFeedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewerFeedbackId, componentVersion, componentId, componentFeedback);
    }
}
