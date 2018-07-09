package DAO.ApplicationSystem;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityComponentversionPK implements Serializable {
    private Integer version;
    private Short componentId;

    @Column(name = "version")
    @Id
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "component_id")
    @Id
    public Short getComponentId() {
        return componentId;
    }

    public void setComponentId(Short componentId) {
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
