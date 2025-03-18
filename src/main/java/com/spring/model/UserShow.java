package com.spring.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserShow implements UserDetails {
    private final User u1;

    public UserShow(User u1) {
        this.u1 = u1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER")); // Use uppercase for roles
    }

    @Override
    public String getPassword() {
        return u1.getPassword();
    }

    @Override
    public String getUsername() {
        return u1.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Or return u1.isAccountNonExpired() if available in User model
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or return u1.isAccountNonLocked() if available
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or return u1.isCredentialsNonExpired() if available
    }

    @Override
    public boolean isEnabled() {
        return true; // Or return u1.isEnabled() if available
    }
}
