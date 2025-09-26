package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
}
