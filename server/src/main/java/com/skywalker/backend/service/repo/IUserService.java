package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.RegisterRequest;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    Response getUserById(Long id);
    Response getAllUsers();
    Response deleteUser(Long id);

    Response register(RegisterRequest request);
    Response login(LoginRequest loginRequest);
}
