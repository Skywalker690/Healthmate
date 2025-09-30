package com.skywalker.backend.controller;

import com.skywalker.backend.dto.PasswordChangeRequest;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.User;
import com.skywalker.backend.service.repo.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Response getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/me/password")
    public Response changePassword(@RequestBody PasswordChangeRequest request) {
        return userService.changePassword(request);
    }

    @GetMapping("/me")
    public Response getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PutMapping("/me")
    public Response updateCurrentUser(@RequestBody User updatedUser) {
        return userService.updateCurrentUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    Response updateUser(Long id, User updatedUser) {
        return userService.updateUser(id,updatedUser);
    }
}
