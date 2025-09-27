package com.skywalker.backend.controller;

import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.RegisterRequest;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.User;
import com.skywalker.backend.service.impl.UserService;
import com.skywalker.backend.service.repo.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
