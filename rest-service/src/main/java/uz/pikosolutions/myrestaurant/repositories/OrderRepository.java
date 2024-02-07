package uz.pikosolutions.myrestaurant.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.pikosolutions.myrestaurant.dto.response.OrderResponse;
import uz.pikosolutions.myrestaurant.entities.Order;

import javax.validation.constraints.NotNull;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_eg")
    Order save(Order order);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_eg")
    Order readById(Long id);
}
