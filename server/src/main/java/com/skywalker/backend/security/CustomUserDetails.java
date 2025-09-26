package com.skywalker.backend.security;

import com.skywalker.backend.model.User;
import com.skywalker.backend.domain.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String password;
    private USER_ROLE role;

    public static CustomUserDetails build(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
