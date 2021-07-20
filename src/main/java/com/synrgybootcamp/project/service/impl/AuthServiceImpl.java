package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Role;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.RoleName;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.RoleRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.JwtTokenUtility;
import com.synrgybootcamp.project.service.AuthService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.DebitCardUtil;
import com.synrgybootcamp.project.util.EmailSender;
import com.synrgybootcamp.project.web.model.request.ChangePasswordRequest;
import com.synrgybootcamp.project.web.model.request.ForgotPasswordRequest;
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

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private DebitCardUtil debitCardUtil;

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
    @Transactional
    public User signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByAccountNumber(signUpRequest.getAccountNumber())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is an account with the same account number");
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is an account with the same email");
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
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid role");
            }
        }

        String profilePicture = "https://m.udustars.com/content/themes/default/images/blank_profile.jpg";
        String cardNumber = debitCardUtil.generate();
        Date expiryDate = debitCardUtil.expiryDate();

        User userResult = userRepository.save(
                User
                        .builder()
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .accountNumber(signUpRequest.getAccountNumber())
                        .telephone(signUpRequest.getTelephone())
                        .balance(signUpRequest.getBalance())
                        .fullName(signUpRequest.getFullName())
                        .profilePicture(profilePicture)
                        .pin(signUpRequest.getPin())
                        .cardNumber(cardNumber)
                        .expiryDate(expiryDate)
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
                        .delete(false)
                        .build()
        );

        return userResult;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        if (userRepository.existsByAccountNumber(forgotPasswordRequest.getAccountNumber())
        && userRepository.existsByEmail(forgotPasswordRequest.getEmail())) {
            User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                    .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "You have entered wrong account number/email"));
            String token = UUID.randomUUID().toString().replace("-","");
            user.setVerifCode(token);
            user.setVerifCodeStatus(true);
            userRepository.save(user);

            String to = forgotPasswordRequest.getEmail();
            String subject = "Password Recovery";
            String link = "https://beasy-ujiaplikasi.herokuapp.com/forgot-password3/" + token;
            String message = buildEmail(user.getFullName(), link);
            emailSender.sendMail(to, subject, message);
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST,"You have entered wrong account number/email");
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        User user = userRepository.findByVerifCode(token)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Token Invalid"));
        if(user.getVerifCodeStatus() == false || user.getVerifCodeStatus() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token Invalid");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        user.setVerifCodeStatus(false);
        userRepository.save(user);
    }

    private String buildEmail(String name, String link) {
        return "<pre>Hello, " + name +
                "<br /><br />" +
                "<br />According to your request, please click the link below to change your password : <a href=\"" + link + "\">Change Password Now</a><br />" +
                "<br />Regards, Beasy Support</pre>";
    }
}
