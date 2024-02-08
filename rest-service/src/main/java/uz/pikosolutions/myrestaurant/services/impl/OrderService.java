package uz.pikosolutions.myrestaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.dto.request.OrderDishRequest;
import uz.pikosolutions.myrestaurant.dto.request.OrderRequest;
import uz.pikosolutions.myrestaurant.dto.response.OrderResponse;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.Order;
import uz.pikosolutions.myrestaurant.entities.auxiliary.OrderDish;
import uz.pikosolutions.myrestaurant.mappers.OrderMapper;
import uz.pikosolutions.myrestaurant.repositories.OrderRepository;
import uz.pikosolutions.myrestaurant.services.BaseService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements BaseService<OrderRequest, OrderResponse, Long> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<OrderResponse> readAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return page.map(orderMapper::entityToDto);
    }

    @Override
    public OrderResponse readById(Long id) {
        return orderMapper.entityToDto(orderRepository.readById(id).get());
    }

    @Transactional
    @Override
    public OrderResponse create(OrderRequest createRequest) {

        createRequest.setOrderDishes(null);

        Order order = orderMapper.dtoToEntity(createRequest);
        Order orderSaved = orderRepository.save(order);
        return orderMapper.entityToDto(orderSaved);
    }

    @Transactional
    @Override
    public void update(Long id, OrderRequest updateRequest) {
        orderRepository.readById(id)
                .map(order -> {
                    System.out.println(order);
                    //orderMapper.toEntity(updateRequest, order);
                    List<OrderDish> orderDishes = new ArrayList<>();

                    List<OrderDishRequest> orderDishRequests = updateRequest.getOrderDishes();
                    for (int i = 0; i < orderDishRequests.size(); i++) {
                        OrderDishRequest orderDishRequest = orderDishRequests.get(i);

                        OrderDish orderDish = new OrderDish();

                        Dish dish = new Dish();
                        dish.setId(orderDishRequest.getDish());
                        orderDish.setDish(dish);
                        orderDish.setCount(orderDishRequest.getCount());
                        orderDish.setOrder(order);
                        orderDishes.add(orderDish);
                    }
                    order.setOrderDishes(orderDishes);

                    System.out.println(order);
                    Order savedComment = orderRepository.save(order);
                    return orderMapper.entityToDto(savedComment);
                }).orElseThrow(() -> new RuntimeException("AAAAA"));
    }

    @Override
    public void deleteById(Long id) {

    }
}
