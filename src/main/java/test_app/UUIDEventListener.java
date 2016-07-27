package test_app;

import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListenerAdapter;

import java.util.UUID;

/**
 * Created by rob on 27/07/16.
 */
public class UUIDEventListener extends EventListenerAdapter {
    @Override
    public void onPreSave(Event event) {
        UUIDEntity entity = (UUIDEntity)event.getObject();
        if (entity.getId() == null)
        {
            entity.setUuid(UUID.randomUUID().toString());
        }
    }
}
