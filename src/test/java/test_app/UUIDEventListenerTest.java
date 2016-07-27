package test_app;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rob on 27/07/16.
 */
public class UUIDEventListenerTest {

    @Before
    public void setup() {
        Configuration neoConfig = new Configuration();
        neoConfig.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");

        sessionFactory = new SessionFactory(neoConfig, "test_app");
    }

    @Test
    public void testUUID() {
        Session session = getSession();

        UUIDEntity entity = new UUIDEntity();
        session.save(entity);

        // This load all gets the only entity in the DB, so it appears to have been saved.
        String uuid = session.loadAll(UUIDEntity.class, 0).iterator().next().getUuid();

        // Now if I do a cypher lookup by uuid
        String cypher = String.format(
                "MATCH (e{uuid:\"%s\"}) RETURN e",
                uuid);

        UUIDEntity fromDBCypher = session.queryForObject(UUIDEntity.class, cypher, Collections.EMPTY_MAP);
        // for me it comes back null ?
        assertThat(fromDBCypher, is(notNullValue()));

        // The same happens if I use a filter lookup
        Filters filters = new Filters();
        filters.add("uuid", uuid);
        Iterable<UUIDEntity> fromDBFilter = session.loadAll(UUIDEntity.class, filters, 0);
        assertThat(fromDBFilter.iterator().hasNext(), is(true));

        // NOTE: If I run this on a 'real' database using the bolt driver. I can see that the
        //       uuid property set in the onPreSave() handler hasn't been saved to entity!
        //       I've tried changing other properties as well and none get persisted.
    }

    private Session getSession() {
        Session session = sessionFactory.openSession();
        listener = new UUIDEventListener();
        session.register(listener);
        return session;
    }

    private SessionFactory sessionFactory;
    private UUIDEventListener listener;
}
