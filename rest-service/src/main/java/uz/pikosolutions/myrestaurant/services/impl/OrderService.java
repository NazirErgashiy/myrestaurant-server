package uz.pikosolutions.myrestaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.dto.request.OrderRequest;
import uz.pikosolutions.myrestaurant.dto.response.OrderResponse;
import uz.pikosolutions.myrestaurant.entities.Order;
import uz.pikosolutions.myrestaurant.mappers.OrderMapper;
import uz.pikosolutions.myrestaurant.repositories.OrderRepository;
import uz.pikosolutions.myrestaurant.services.BaseService;

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
        return orderMapper.entityToDto(orderRepository.readById(id));
    }

    @Transactional
    @Override
    public OrderResponse create(OrderRequest createRequest) {

        Order order = orderMapper.dtoToEntity(createRequest);
        Order orderSaved = orderRepository.save(order);
        return orderMapper.entityToDto(orderSaved);
    }

    @Override
    public void update(Long id, OrderRequest updateRequest) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
