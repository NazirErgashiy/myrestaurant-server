package uz.pikosolutions.myrestaurant.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.pikosolutions.myrestaurant.entities.Dish;

@Repository
public interface DishRepository extends CrudRepository<Dish,Long>, PagingAndSortingRepository<Dish,Long> {

}
