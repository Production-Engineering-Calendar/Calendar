package ro.unibuc.hello.service;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.model.Notificare;

@Component
public class NotificareService {
    
    @Autowired
    private InformationRepository informationRepository;

    public NotificareService() {
    }

    public Notificare getNotificareByEventId(int eventId) {
        return informationRepository.findByEventId(eventId);
    }
}
