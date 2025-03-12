package ro.unibuc.hello.model;

import java.util.Date;
import java.util.List;

public class Event {
    private int eventId;
    private String name;
    private Date date;
    private String location;
    private int userId;
    private List<String> usernames;

    // Constructors, getters, and setters
    public Event() {}

    public Event(int eventId, String name, Date date, String location, int userId, List<String> usernames) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.location = location;
        this.userId = userId;
        this.usernames = usernames;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}