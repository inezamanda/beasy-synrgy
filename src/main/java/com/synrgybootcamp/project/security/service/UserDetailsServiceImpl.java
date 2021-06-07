package com.synrgybootcamp.project.security.service;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(roleAccess -> new SimpleGrantedAuthority(roleAccess.getRoleName().name()))
                .collect(Collectors.toList()))
                .build();
    }
}
