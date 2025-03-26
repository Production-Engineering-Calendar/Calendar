// --- EventServiceTest.java ---
package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.repository.EventRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setName("Test Event");
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertEquals("Test Event", result.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testGetEventById() {
        Event event = new Event();
        event.setEventId("123");
        when(eventRepository.findByEventId("123")).thenReturn(Optional.of(event));

        Event result = eventService.getEventById("123");

        assertEquals("123", result.getEventId());
        verify(eventRepository, times(1)).findByEventId("123");
    }

    @Test
    public void testDeleteEvent_found() {
        when(eventRepository.existsById("123")).thenReturn(true);

        boolean result = eventService.deleteEvent("123");

        assertTrue(result);
        verify(eventRepository, times(1)).deleteById("123");
    }

    @Test
    public void testDeleteEvent_notFound() {
        when(eventRepository.existsById("456")).thenReturn(false);

        boolean result = eventService.deleteEvent("456");

        assertFalse(result);
        verify(eventRepository, never()).deleteById("456");
    }

    @Test
    public void testGetEventsByUserId() {
        List<Event> mockEvents = Arrays.asList(new Event(), new Event());
        when(eventRepository.findByUserId("user1")).thenReturn(mockEvents);

        List<Event> result = eventService.getEventsByUserId("user1");

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findByUserId("user1");
    }

    @Test
    public void testInviteUser_found() {
        Event event = new Event();
        event.setEventId("e1");
        when(eventRepository.findByEventId("e1")).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);

        boolean result = eventService.inviteUser("e1", "newUser");

        assertTrue(event.getUsernames().contains("newUser"));
        assertTrue(result);
        verify(eventRepository).save(event);
    }

    @Test
    public void testInviteUser_notFound() {
        when(eventRepository.findByEventId("e999")).thenReturn(Optional.empty());

        boolean result = eventService.inviteUser("e999", "newUser");

        assertFalse(result);
        verify(eventRepository, never()).save(any(Event.class));
    }
}