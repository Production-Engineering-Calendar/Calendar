package ro.unibuc.hello.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.repository.EventRepository;
import ro.unibuc.hello.repository.NotificareRepository;
import ro.unibuc.hello.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificareRepository notificareRepository;

    @Mock
    private MeterRegistry metricsRegistry;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Stub metric counter globally
        Counter mockCounter = Mockito.mock(Counter.class);
        when(metricsRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mockCounter);
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setName("Test Event");
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertNotNull(result);
        assertEquals("Test Event", result.getName());
        verify(eventRepository).save(event);
    }

    @Test
    public void testGetEventById() {
        Event event = new Event();
        event.setEventId("123");
        when(eventRepository.findByEventId("123")).thenReturn(Optional.of(event));

        Event result = eventService.getEventById("123");

        assertNotNull(result);
        assertEquals("123", result.getEventId());
        verify(eventRepository).findByEventId("123");
    }

    @Test
    public void testDeleteEvent_found() {
        when(eventRepository.existsById("123")).thenReturn(true);

        boolean result = eventService.deleteEvent("123");

        assertTrue(result);
        verify(eventRepository).deleteById("123");
    }

    @Test
    public void testDeleteEvent_notFound() {
        when(eventRepository.existsById("456")).thenReturn(false);

        boolean result = eventService.deleteEvent("456");

        assertFalse(result);
        verify(eventRepository, never()).deleteById(anyString());
    }

    @Test
    public void testGetEventsByUserId() {
        List<Event> mockEvents = Arrays.asList(new Event(), new Event());
        when(eventRepository.findByUserId("user1")).thenReturn(mockEvents);

        List<Event> result = eventService.getEventsByUserId("user1");

        assertEquals(2, result.size());
        verify(eventRepository).findByUserId("user1");
    }

    @Test
    public void testInviteUser_found() {
        Event event = new Event();
        event.setEventId("e1");
        event.setUsernames(new ArrayList<>());

        when(eventRepository.findByEventId("e1")).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);

        boolean result = eventService.inviteUser("e1", "newUser");

        assertTrue(result);
        assertTrue(event.getUsernames().contains("newUser"));
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
