package uz.pikosolutions.myrestaurant.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long id;
    private Integer personsCount;
    private Integer service;
    private Long creator;
    private List<OrderDishRequest> orderDishes;
}
