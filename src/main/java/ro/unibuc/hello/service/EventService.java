package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.model.Notificare;
import ro.unibuc.hello.repository.EventRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.NotificareRepository;
import ro.unibuc.hello.model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;



@Service
public class EventService {
    private final EventRepository eventRepository;

    private MeterRegistry metricsRegistry;

    private final NotificareRepository notificareRepository;
    
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, NotificareRepository notificareRepository, UserRepository userRepository, MeterRegistry metricsRegistry) {
        this.notificareRepository = notificareRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.metricsRegistry = metricsRegistry;
    }

    public Event addEvent(Event event) {
        metricsRegistry.counter("metric-add-event", "endpoint", "addEvent").increment(1);
        return eventRepository.save(event);
    }

    public Event getEventById(String eventId) {
        metricsRegistry.counter("times-getEventById", "endpoint", "getEventById").increment(1);
        return eventRepository.findByEventId(eventId).orElse(null);
    }

    public boolean deleteEvent(String eventId) {
        metricsRegistry.counter("times-deleteEvent", "endpoint", "deleteEvent").increment(1);
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    public List<Event> getEventsByUserId(String userId) {
        metricsRegistry.counter("times-getEventsByUserId", "endpoint", "getEventsByUserId").increment(1);
        return eventRepository.findByUserId(userId);
    }

    public List<Event> getEventsByUsername(String username) {
        metricsRegistry.counter("times-getEventsByUsername", "endpoint", "getEventsByUsername").increment(1);
        return eventRepository.findByUsernamesContaining(username);
    }

    public boolean inviteUser(String eventId, String username) {
        metricsRegistry.counter("times-inviteUser", "endpoint", "inviteUser").increment(1);
        Event event = eventRepository.findByEventId(eventId).orElse(null);
        if (event != null) {
            event.addInvite(username);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public List<String> getAcceptedUsernamesForEvent(String eventId) {
        metricsRegistry.counter("times-accepted", "endpoint", "getAcceptedUsernamesforEvent").increment(1);
        Event event = eventRepository.findByEventId(eventId).orElse(null);
        if (event == null) return Collections.emptyList();

        List<String> invitedUsernames = event.getUsernames();
        Set<String> acceptedUsernames = new HashSet<>();

        List<Notificare> acceptedNotificari = notificareRepository.findByEventId(eventId);

        for (String username : invitedUsernames) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                String userId = userOpt.get().getId();
    
                boolean isAccepted = acceptedNotificari.stream()
                .anyMatch(n ->
                    n.getUserId().equals(userId)
                    && n.getVerificare()
                    && "invitat".equalsIgnoreCase(n.getTipVerificare())
                );
    
                if (isAccepted) {
                    acceptedUsernames.add(username);
                }
            }
        }

        return new ArrayList<>(acceptedUsernames);
    }
}
