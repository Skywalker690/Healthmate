package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.LoginDTO;
import com.skywalker.backend.dto.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String registerUser(Response dto);

    String loginUser(LoginDTO dto);

    boolean validateToken(String token);
}
