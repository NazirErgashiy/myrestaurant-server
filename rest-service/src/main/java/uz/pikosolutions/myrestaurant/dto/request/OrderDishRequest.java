package uz.pikosolutions.myrestaurant.dto.request;

import lombok.Data;
import uz.pikosolutions.myrestaurant.entities.Dish;

@Data
public class OrderDishRequest {
    private Long order;
    private Long dish;
    private Float count;
}
