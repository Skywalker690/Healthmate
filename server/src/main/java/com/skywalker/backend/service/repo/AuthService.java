package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String registerUser(Response dto);

    String loginUser(LoginRequest dto);

    boolean validateToken(String token);
}
