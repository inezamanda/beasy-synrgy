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

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
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
            String link = "http://localhost:8080/api/auth/change-password/" + token;
            String message = buildEmail(user.getFullName(), link);
            emailSender.sendMail(to, subject, message);
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST,"You have entered wrong account number/email");
        }
        return "Email Sended";
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest, String token) {
        User user = userRepository.findByVerifCode(token)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Token Invalid"));
        if(user.getVerifCodeStatus() == false || user.getVerifCodeStatus() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token Invalid");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        user.setVerifCodeStatus(false);
        userRepository.save(user);
        return "Password Changed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Pasword Recovery Request</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> According to your request, please click the link below to change your password : </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Change Password Now</a></p></blockquote> <p>Regards, <br>Beasy Support</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
