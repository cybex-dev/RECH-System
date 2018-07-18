package DAO.ApplicationSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntityComponentversionPK implements Serializable {
    private Short version;
    private Integer componentId;

    public EntityComponentversionPK() {
    }

    @Column(name = "version", nullable = false)
    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
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
        EntityComponentversionPK that = (EntityComponentversionPK) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(componentId, that.componentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, componentId);
    }
}
