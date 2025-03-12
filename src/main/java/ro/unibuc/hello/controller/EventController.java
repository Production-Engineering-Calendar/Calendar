package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private List<Event> eventList = new ArrayList<>();
    
    // Add Event
    @PostMapping("/add")
    public Event addEvent(@RequestBody Event event) {
        eventList.add(event);
        return event; // Return added event
    }

    // Delete Event
    @DeleteMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable int eventId) {
        Optional<Event> event = eventList.stream()
                .filter(e -> e.getEventId() == eventId)
                .findFirst();

        if (event.isPresent()) {
            eventList.remove(event.get());
            return "Event with ID " + eventId + " deleted successfully.";
        } else {
            return "Event with ID " + eventId + " not found.";
        }
    }

    // Get all events by userId
    @GetMapping("/user/{userId}")
    public List<Event> getEventsByUserId(@PathVariable int userId) {
        List<Event> eventsByUser = new ArrayList<>();
        for (Event event : eventList) {
            if (event.getUserId() == userId) {
                eventsByUser.add(event);
            }
        }
        return eventsByUser;
    }

    // Get all events by username
    @GetMapping("/username/{username}")
    public List<Event> getEventsByUsername(@PathVariable String username) {
        List<Event> eventsByUsername = new ArrayList<>();
        for (Event event : eventList) {
            if (event.getUsernames().contains(username)) {
                eventsByUsername.add(event);
            }
        }
        return eventsByUsername;
    }

    // Add an invitation
    @PostMapping("/invite/{eventId}/{username}")
    public String inviteUser(@PathVariable int eventId, @PathVariable String username) {
        Optional<Event> event = eventList.stream()
                .filter(e -> e.getEventId() == eventId)
                .findFirst();

        if (event.isPresent()) {
            event.get().getUsernames().add(username);
            return "User " + username + " has been invited to event " + eventId;
        } else {
            return "Event with ID " + eventId + " not found.";
        }
    }
}
