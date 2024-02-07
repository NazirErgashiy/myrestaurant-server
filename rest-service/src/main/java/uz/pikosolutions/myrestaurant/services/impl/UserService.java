package uz.pikosolutions.myrestaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.entities.User;
import uz.pikosolutions.myrestaurant.repositories.UserRepository;
import uz.pikosolutions.myrestaurant.services.BaseService;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User, User, Long> {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<User> readAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User readById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public User create(User createRequest) {
        return userRepository.save(createRequest);
    }

    @Override
    public void update(Long id, User updateRequest) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
