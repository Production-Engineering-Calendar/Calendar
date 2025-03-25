package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.NotificareService;
import ro.unibuc.hello.model.Notificare;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class NotificareController {

    @Autowired
    private NotificareService notificareService;

    @GetMapping("/notificare")
    @ResponseBody
    public Notificare notificare(@RequestParam(name="eventId", required=false, defaultValue="0") String eventId) {
        return notificareService.getNotificareByEventId(eventId);
    }

    @PostMapping("/notificare")
    @ResponseBody
    public Notificare createNotificare(@RequestBody Notificare notificare) {
        return notificareService.createNotificare(notificare);
    }

    @GetMapping("/notificare/{eventId}")
    @ResponseBody
    public List<Notificare> getNotificariByEventId(@PathVariable String eventId) {
        return notificareService.getNotificareByEventId(eventId);
    }

    @GetMapping("/notificare/user/{userId}")
    @ResponseBody
    public List<Notificare> getNotificareByUserId(@PathVariable String userId) {
        return notificareService.getNotificareByUserId(userId);
    }

    @PostMapping("/notificare/user/{userId}")
    @ResponseBody
    public void createNotificaribyUserId(@PathVariable String userId) {
        notificareService.createNotificaribyUserId(userId);
    }

    @PostMapping("/notificare/{notificareId}/accept")
    @ResponseBody
    public void acceptInvitation(@PathVariable String notificareId) {
        notificareService.acceptInvitation(notificareId);
    }
   
}