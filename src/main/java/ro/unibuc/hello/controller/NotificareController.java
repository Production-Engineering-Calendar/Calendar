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
    public Notificare notificare(@RequestParam(name="eventId", required=false, defaultValue=0) int eventId) {
        return notificareService.getNotificareByEventId(eventId);
    }

    @PostMapping("/notificare")
    @ResponseBody
    public Notificare createNotificare(@RequestBody Notificare notificare) {
        return notificareService.createNotificare(notificare);
    }

    @GetMapping("/notificare/{eventId}")
    @ResponseBody
    public Notificare getNotificareByEventId(@PathVariable int eventId) {
        return notificareService.getNotificareByEventId(eventId);
    }

    // @GetMapping("/notificari")
    // @ResponseBody
    // public List<Notificare> getAllNotificari() {
    //     return notificareService.getAllNotificari();
    // }


   
}