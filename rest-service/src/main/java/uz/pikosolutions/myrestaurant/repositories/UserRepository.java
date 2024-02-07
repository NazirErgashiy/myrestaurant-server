package uz.pikosolutions.myrestaurant.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>, PagingAndSortingRepository<User,Long> {

}
