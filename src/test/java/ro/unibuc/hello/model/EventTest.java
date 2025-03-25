// --- EventTest.java ---
package ro.unibuc.hello.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EventTest {

    @Test
    void test_eventId() {
        Event event = new Event();
        event.setEventId("e123");
        Assertions.assertEquals("e123", event.getEventId());
    }

    @Test
    void test_userId() {
        Event event = new Event();
        event.setUserId("u456");
        Assertions.assertEquals("u456", event.getUserId());
    }

    @Test
    void test_name() {
        Event event = new Event();
        event.setName("Birthday Party");
        Assertions.assertEquals("Birthday Party", event.getName());
    }

    @Test
    void test_description() {
        Event event = new Event();
        event.setDescription("Celebration with friends");
        Assertions.assertEquals("Celebration with friends", event.getDescription());
    }

    @Test
    void test_usernames() {
        Event event = new Event();
        event.setUsernames(List.of("user1", "user2"));
        Assertions.assertEquals(2, event.getUsernames().size());
        Assertions.assertTrue(event.getUsernames().contains("user1"));
    }

    @Test
    void test_addInvite() {
        Event event = new Event();
        event.addInvite("newUser");
        Assertions.assertTrue(event.getUsernames().contains("newUser"));
    }
}
