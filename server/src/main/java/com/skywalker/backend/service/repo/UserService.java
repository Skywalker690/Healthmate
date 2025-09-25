package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, Response dto);

    void deleteUser(Long id);
}
