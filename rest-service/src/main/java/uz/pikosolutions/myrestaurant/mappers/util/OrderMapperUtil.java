package uz.pikosolutions.myrestaurant.mappers.util;

import org.springframework.stereotype.Component;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.entities.User;
import uz.pikosolutions.myrestaurant.entities.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapperUtil {

    public User mapUser(Long value) {
        User user = new User();
        user.setId(value);
        //user.setRole(Role.USER);
        return user;
    }

    public List<Dish> map(List<Long> value) {

        List<Dish> result = new ArrayList<>();

        for (int i = 0; i < value.size(); i++) {
            Dish dish = new Dish();
            dish.setId(value.get(i));
            result.add(dish);
        }
        return result;
    }
}
