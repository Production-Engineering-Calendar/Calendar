package ro.unibuc.hello.service;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ro.unibuc.hello.repository.NotificareRepository;
import ro.unibuc.hello.model.Notificare;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.service.EventService;

@Component
public class NotificareService {
    
    @Autowired
    private NotificareRepository notificareRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    public NotificareService() {
    }

    public List<Notificare> getNotificariByEventId(String eventId) {
        return notificareRepository.findByEventId(eventId);
    }

    public Notificare createNotificare(Notificare notificare) {
        return notificareRepository.save(notificare);
    }

    // create notifications for events of an user and for the invited users of those events
    public void createNotificaribyUserId(String userId) {
        List<Notificare> notificari = notificareRepository.findByUserId(userId);

        eventService.getEventsByUserId(userId).forEach(event -> {

            // check if notifications have already been created for this event
            boolean hasNotification = notificari.stream()
                .anyMatch(notificare -> notificare.getEventId() == event.getEventId());
            if (!hasNotification) {

                Notificare notificare = new Notificare();
                notificare.setEventId(event.getEventId());
                notificare.setUserId(userId);
                notificare.setTipVerificare("creator");
                notificare.setVerificare(true);
                createNotificare(notificare);
                
                event.getUsernames.forEach(username -> {
                    notificareInvitat = new Notificare();
                    notificareInvitat.setEventId(event.getEventId());
                    userRepository.findByUsername(username).ifPresent(user -> {
                        notificareInvitat.setUserId(user.getUserId());
                    });
                    notificareInvitat.setUserId(userId);
                    notificareInvitat.setTipVerificare("invitat");
                    notificareInvitat.setVerificare(false);
                    createNotificare(notificareInvitat);
                });

            }

        });

    }

    public List<Notificare> getNotificariByUserId(String userId) {
        return notificareRepository.findByUserId(userId);
    }


    public void acceptInvitation(String notificareId) {
        Notificare notificare = notificareRepository.findById(notificareId);
        notificare.setVerificare(true);
        notificareRepository.save(notificare);
    }
}
