package uz.pikosolutions.myrestaurant.dto.response;

import lombok.Data;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Integer personsCount;
    private Integer service;
    private Float cost;
    private User creator;
    private List<Dish> dishes;
    private List<Float> dishCount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
