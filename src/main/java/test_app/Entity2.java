package test_app;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by rob on 26/07/16.
 */
@NodeEntity(label = "Entity2")
public class Entity2 {

    public Entity2() {
    }

    public Entity2(String property) {
        this.property = property;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Entity1 getOtherOf() {
        return otherOf;
    }

    public void setOtherOf(Entity1 otherOf) {
        this.otherOf = otherOf;
    }

    Long id;
    private String property;
    @Relationship(type = "OtherOf", direction = Relationship.INCOMING)
    Entity1 otherOf;
}
