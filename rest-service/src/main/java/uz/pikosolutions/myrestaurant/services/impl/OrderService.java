package uz.pikosolutions.myrestaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.dto.request.OrderDishRequest;
import uz.pikosolutions.myrestaurant.dto.request.OrderRequest;
import uz.pikosolutions.myrestaurant.dto.response.OrderResponse;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.Order;
import uz.pikosolutions.myrestaurant.entities.User;
import uz.pikosolutions.myrestaurant.entities.auxiliary.OrderDish;
import uz.pikosolutions.myrestaurant.mappers.OrderMapper;
import uz.pikosolutions.myrestaurant.repositories.OrderRepository;
import uz.pikosolutions.myrestaurant.repositories.UserRepository;
import uz.pikosolutions.myrestaurant.services.BaseService;
import uz.pikosolutions.service.jwt.TokenAuthentication;
import uz.pikosolutions.service.jwt.TokenUser;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements BaseService<OrderRequest, OrderResponse, Long> {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
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

        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        TokenUser tokenUser = (TokenUser) authentication.getPrincipal();

        if (!userRepository.existsById(tokenUser.getId())) {
            User user = new User();
            user.setId(tokenUser.getId());
            user.setName(tokenUser.getLogin());
            userRepository.save(user);
        }
        createRequest.setOrderDishes(null);

        Order order = orderMapper.dtoToEntity(createRequest);
        User creator = new User();
        creator.setId(tokenUser.getId());
        creator.setName(tokenUser.getLogin());
        order.setCreator(creator);

        Order orderSaved = orderRepository.save(order);
        return orderMapper.entityToDto(orderSaved);
    }

    @Transactional
    @Override
    public void update(Long id, OrderRequest updateRequest) {

        orderRepository.readById(id)
                .map(order -> {
                    orderMapper.toEntity(updateRequest, order);
                    Order savedComment = orderRepository.save(order);
                    return orderMapper.entityToDto(savedComment);
                }).orElseThrow(() -> new RuntimeException("AAAAA"));
    }

    @Override
    public void deleteById(Long id) {

    }
}
