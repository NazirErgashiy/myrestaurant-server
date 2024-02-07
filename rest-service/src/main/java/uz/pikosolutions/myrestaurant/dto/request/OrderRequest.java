package uz.pikosolutions.myrestaurant.dto.request;

import lombok.Data;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.User;

import java.util.List;

@Data
public class OrderRequest {
    private Long id;
    private Integer personsCount;
    private Integer service;
    private Long creator;
    private List<Long> dishes;
    private List<Float> dishCount;
}
