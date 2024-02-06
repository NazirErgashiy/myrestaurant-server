package uz.pikosolutions.myrestaurant.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.pikosolutions.myrestaurant.auth.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByLogin(String login);
}
