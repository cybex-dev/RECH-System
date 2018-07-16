package DAO.ApplicationSystem;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "component", schema = "rech_system", catalog = "")
public class EntityComponent extends Model {
    private Integer componentId;
    private String questionId;
    private Integer applicationId;

    public static Finder<Integer, EntityComponent> find = new Finder<>(EntityComponent.class);

    @Id
    @Column(name = "component_id")
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @Basic
    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "application_id")
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityComponent that = (EntityComponent) o;
        return Objects.equals(componentId, that.componentId) &&
                Objects.equals(questionId, that.questionId) &&
                Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(componentId, questionId, applicationId);
    }
}
