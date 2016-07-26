package test_app;

import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListener;

import java.util.HashMap;

/**
 * Created by rob on 26/07/16.
 */
public class CustomEventListener implements EventListener {
    public CustomEventListener() {
        entity1Count = createEmptyMap();
        entity2Count = createEmptyMap();
        relCount = createEmptyMap();
    }

    @Override
    public void onPreSave(Event event) {
        updateCounts(event);
    }

    @Override
    public void onPostSave(Event event) {
        updateCounts(event);
    }

    @Override
    public void onPreDelete(Event event) {
        updateCounts(event);
    }

    @Override
    public void onPostDelete(Event event) {
        updateCounts(event);
    }

    public int getEnitiy1Count(Event.TYPE type) {
        return entity1Count.get(type);
    }

    public int getEntity2Count(Event.TYPE type) {
        return entity2Count.get(type);
    }

    public int getRelCount(Event.TYPE type) {
        return relCount.get(type);
    }

    private void updateCounts(Event event) {
        Event.TYPE type = event.getLifeCycle();
        if (event.getObject() instanceof Entity1) {
            entity1Count.put(type, entity1Count.get(type)+1);
        }
        else if (event.getObject() instanceof Entity2) {
            entity2Count.put(type, entity2Count.get(type)+1);
        }
        // not sure what type the rel is .
        else {
            relCount.put(type, relCount.get(type)+1);
        }
    }

    private HashMap<Event.TYPE, Integer> createEmptyMap() {
        HashMap<Event.TYPE, Integer> map = new HashMap<>();
        map.put(Event.TYPE.PRE_SAVE, 0);
        map.put(Event.TYPE.POST_SAVE, 0);
        map.put(Event.TYPE.PRE_DELETE, 0);
        map.put(Event.TYPE.POST_DELETE, 0);
        return map;
    }

    private HashMap<Event.TYPE, Integer> entity1Count;
    private HashMap<Event.TYPE, Integer> entity2Count;
    private HashMap<Event.TYPE, Integer> relCount;
}
