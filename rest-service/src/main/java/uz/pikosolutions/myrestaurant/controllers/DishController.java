package uz.pikosolutions.myrestaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.services.impl.DishService;

@RestController
@RequestMapping(path = "/api/v1/dishes", produces = "application/json")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Dish> readPaged(
            @PageableDefault(page = 0, size = 50)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        return dishService.readAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dish create(@RequestBody Dish request) {
        return dishService.create(request);
    }
}
