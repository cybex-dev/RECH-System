package DAO.ReviewSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityReviewercomponentfeedbackPK implements Serializable {
    private Integer reviewerFeedbackId;
    private Short componentVersion;
    private Integer componentId;

    @Column(name = "reviewer_feedback_id")
    @Id
    public Integer getReviewerFeedbackId() {
        return reviewerFeedbackId;
    }

    public void setReviewerFeedbackId(Integer reviewerFeedbackId) {
        this.reviewerFeedbackId = reviewerFeedbackId;
    }

    @Column(name = "component_version")
    @Id
    public Short getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(Short componentVersion) {
        this.componentVersion = componentVersion;
    }

    @Column(name = "component_id")
    @Id
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
        EntityReviewercomponentfeedbackPK that = (EntityReviewercomponentfeedbackPK) o;
        return Objects.equals(reviewerFeedbackId, that.reviewerFeedbackId) &&
                Objects.equals(componentVersion, that.componentVersion) &&
                Objects.equals(componentId, that.componentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewerFeedbackId, componentVersion, componentId);
    }
}
