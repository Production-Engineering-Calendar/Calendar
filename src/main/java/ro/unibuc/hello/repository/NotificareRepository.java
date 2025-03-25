package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.model.Notificare;
import java.util.Optional;

@Repository
public interface NotificareRepository extends MongoRepository<Notificare, String> {
    Notificare findByNotificareId(String notificareId);
    List<Notificare> findByEventId(int eventId);
    List<Notificare> findByUserId(int userId);
}
