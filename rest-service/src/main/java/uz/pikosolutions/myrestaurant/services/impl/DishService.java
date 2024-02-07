package uz.pikosolutions.myrestaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.entities.Dish;
import uz.pikosolutions.myrestaurant.repositories.DishRepository;
import uz.pikosolutions.myrestaurant.services.BaseService;

@Service
@RequiredArgsConstructor
public class DishService implements BaseService<Dish, Dish,Long> {

    private final DishRepository dishRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Dish> readAll(Pageable pageable) {
        return dishRepository.findAll(pageable);
    }

    @Override
    public Dish readById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public Dish create(Dish createRequest) {
        return dishRepository.save(createRequest);
    }

    @Override
    public void update(Long id, Dish updateRequest) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
