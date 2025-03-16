package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.model.Role;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
