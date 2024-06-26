package com.wiktormalyska.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Getter
@Table(name = "users")
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Setter
    private String username;
    @Setter
    private String password;
    private Date creationDate;
    private boolean isAdmin;

    public User() {
        this.creationDate = new Date();
    }
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.creationDate = new Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
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
