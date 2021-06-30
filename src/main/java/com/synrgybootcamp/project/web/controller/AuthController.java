package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.service.impl.AuthServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ErrorResponse;
import com.synrgybootcamp.project.web.model.request.ChangePasswordRequest;
import com.synrgybootcamp.project.web.model.request.ForgotPasswordRequest;
import com.synrgybootcamp.project.web.model.request.SignInRequest;
import com.synrgybootcamp.project.web.model.request.SignUpRequest;
import com.synrgybootcamp.project.web.model.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    GamificationHelper gamificationHelper;

    @PostMapping("signin")
    public ResponseEntity<Object> signIn(
            @RequestBody SignInRequest signInRequest
    ) {
        SignInResponse signInResponse = authService.signIn(signInRequest);

        return new ResponseEntity<>(
                new ApiResponse("Sign In Success", signInResponse)
                , HttpStatus.OK
        );
    }

    @PostMapping("signup")
    public ResponseEntity<Object> signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        User user = authService.signUp(signUpRequest);
        if (user == null) {
            return new ResponseEntity<>(
                    new ErrorResponse(false,"There is an account with the same email/account_number")
                    , HttpStatus.BAD_REQUEST
            );
        } else {
            gamificationHelper.startGamificationForNewUser(user);

            return new ResponseEntity<>(
                    new ApiResponse("Account Created")
                    , HttpStatus.OK
            );
        }
    }

    @PostMapping("forgot-password")
    public ResponseEntity<Object> forgotPassword(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest
    ) {
        return new ResponseEntity<>(
                new ApiResponse(authService.forgotPassword(forgotPasswordRequest))
        , HttpStatus.OK);
    }

    @PostMapping("change-password/{token}")
    public ResponseEntity<Object> changePassword(
            @PathVariable String token,
            @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        authService.changePassword(changePasswordRequest, token);
        return new ResponseEntity<>(
                new ApiResponse("Password Changed")
        , HttpStatus.OK);
    }
}
