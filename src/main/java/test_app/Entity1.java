package test_app;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by rob on 26/07/16.
 */
@NodeEntity(label = "Entity1")
public class Entity1 {

    public Entity1() {
    }

    public Entity1(String property) {
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

    public Entity2 getOther() {
        return other;
    }

    public void setOther(Entity2 other) {
        this.other = other;
    }

    Long id;
    private String property;
    @Relationship(type = "Other", direction = Relationship.OUTGOING)
    Entity2 other;
}
