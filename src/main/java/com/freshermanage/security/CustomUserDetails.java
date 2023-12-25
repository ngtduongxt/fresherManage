package com.freshermanage.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freshermanage.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String fullName;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private boolean userStatus;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public static CustomUserDetails mapUserToUserDetail(Users users) {
        List<GrantedAuthority> listAuthorities = users.getListRole().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetails(
                users.getId(),
                users.getFullName(),
                users.getUserName(),
                users.getPassword(),
                users.getEmail(),
                users.isUserStatus(),
                listAuthorities
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
