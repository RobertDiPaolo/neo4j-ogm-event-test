package test_app;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by rob on 27/07/16.
 */
@NodeEntity(label = "UUIDEntity")
public class UUIDEntity {

    public UUIDEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private Long id;
    private String uuid;
}
