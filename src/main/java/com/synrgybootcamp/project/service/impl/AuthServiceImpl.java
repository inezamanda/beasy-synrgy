package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Role;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.RoleName;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.RoleRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.model.UserDetailsImpl;
import com.synrgybootcamp.project.security.utility.JwtTokenUtility;
import com.synrgybootcamp.project.service.AuthService;
import com.synrgybootcamp.project.web.model.request.SignInRequest;
import com.synrgybootcamp.project.web.model.request.SignUpRequest;
import com.synrgybootcamp.project.web.model.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtility.generateJwtToken(authentication);
        return SignInResponse
                .builder()
                .token(token)
                .build();
    }

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByAccountNumber(signUpRequest.getAccountNumber())) {
            return null;
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return null;
        }

        Set<Role> roles = new HashSet<>();
        String[] roleArr = signUpRequest.getRoles();

        if(roleArr == null) {
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
        }
        for(String role: roleArr) {
            switch(role.toLowerCase()) {
                case "admin":
                    roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).get());
                    break;
                case "user":
                    roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
                    break;
                default:
                    return null;
            }
        }

        User userResult = userRepository.save(
                User
                        .builder()
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .accountNumber(signUpRequest.getAccountNumber())
                        .telephone(signUpRequest.getTelephone())
                        .balance(signUpRequest.getBalance())
                        .fullName(signUpRequest.getFullName())
                        .pin(signUpRequest.getPin())
                        .roles(roles)
                        .build()
        );

        pocketRepository.save(
                Pocket.builder()
                        .user(userResult)
                        .name("Primary Pocket " + signUpRequest.getFullName())
                        .balance(userResult.getBalance())
                        .picture(null)
                        .primary(true)
                        .target(0)
                        .build()
        );

        return userResult;
    }
}
