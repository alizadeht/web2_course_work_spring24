package com.example.assignment1.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;
    private String roles;

    @Transient
    private List<String> authorities = new ArrayList<>(List.of("ROLE_USER"));

    public List<GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(
                        SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addRole(String authority) {
        this.authorities.add(authority);
    }

    @PrePersist
    @PreUpdate
    private void saveRoles() {
        this.roles = String.join(";", this.authorities);
    }

    @PostLoad
    private void readRoles() {
        this.authorities = Arrays.stream(this.roles.split(";"))
                .collect(Collectors.toList());
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
