package test_app;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.transaction.Transaction;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rob on 26/07/16.
 */
public class CustomEventListenerTest {

    @Before
    public void setup() {
        Configuration neoConfig = new Configuration();
        neoConfig.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");

        sessionFactory = new SessionFactory(neoConfig, "test_app");
    }

    @Test
    public void testRelEvents() {
        Session session = getSession();
        try (Transaction trans = session.beginTransaction())
        {
            Entity1 e1 = new Entity1("1");
            session.save(e1);

            Entity2 e2 = new Entity2("2");
            session.save(e2);

            e1.setOther(e2);
            e2.setOtherOf(e1);

            session.save(e1, 1);
            session.save(e2, 1);

            trans.commit();
        }

        // check rels were saved
        Entity1 e1 = session.loadAll(Entity1.class).iterator().next();
        assertThat(e1.getOther(), is(notNullValue()));

        Entity2 e2 = session.loadAll(Entity2.class).iterator().next();
        assertThat(e2.getOtherOf(), is(notNullValue()));

        // Should have got at lest one of each Entity Events
        assertThat(listener.getEnitiy1Count(Event.TYPE.PRE_SAVE), is(greaterThanOrEqualTo(1)));
        assertThat(listener.getEnitiy1Count(Event.TYPE.POST_SAVE), is(greaterThanOrEqualTo(1)));
        assertThat(listener.getEntity2Count(Event.TYPE.PRE_SAVE), is(greaterThanOrEqualTo(1)));
        assertThat(listener.getEntity2Count(Event.TYPE.POST_SAVE), is(greaterThanOrEqualTo(1)));

        // So now I'd expect the relationship creation to have emitted an event
        assertThat(listener.getRelCount(Event.TYPE.PRE_SAVE), is(greaterThanOrEqualTo(1)));
        assertThat(listener.getRelCount(Event.TYPE.POST_SAVE), is(greaterThanOrEqualTo(1)));

        // NOTE: I've not tested in it this test app but in my actual app, if I create a custom
        //       class for the relationship. I get notified in both the pre/post save handlers.
    }

    private Session getSession() {
        Session session = sessionFactory.openSession();
        listener = new CustomEventListener();
        session.register(listener);
        return session;
    }

    private SessionFactory sessionFactory;
    private CustomEventListener listener;
}
