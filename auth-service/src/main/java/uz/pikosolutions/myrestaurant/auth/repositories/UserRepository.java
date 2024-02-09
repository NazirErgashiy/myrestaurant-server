package uz.pikosolutions.myrestaurant.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.pikosolutions.service.entity.AuthUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AuthUser,Long> {
    Optional<AuthUser> findByLogin(String login);
}
