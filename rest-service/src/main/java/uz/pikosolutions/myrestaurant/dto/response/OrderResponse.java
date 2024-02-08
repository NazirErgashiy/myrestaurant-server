package uz.pikosolutions.myrestaurant.dto.response;

import lombok.Data;
import uz.pikosolutions.myrestaurant.entities.User;
import uz.pikosolutions.myrestaurant.entities.auxiliary.OrderDish;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Integer personsCount;
    private Integer service;
    private Float cost;
    private User creator;
    private List<OrderDish> orderDishes;
    //private List<OrderDish> dishCount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
