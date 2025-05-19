package com.jktechproject.documentmanagement.service;

import com.jktechproject.documentmanagement.entity.User;
import com.jktechproject.documentmanagement.repository.UserRepoRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailSErviceImplementation implements UserDetailsService {

    private final UserRepoRole userRepoRole;

    public UserDetailSErviceImplementation(UserRepoRole userRepoRole) {
        this.userRepoRole = userRepoRole;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User client  = userRepoRole.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User name is invalid "+username));

        Set<SimpleGrantedAuthority> authorities = client.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                client.getUsername(),
                client.getPassword(),
                authorities
        );

    }
}
