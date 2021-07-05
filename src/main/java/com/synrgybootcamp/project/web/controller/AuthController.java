package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.service.AuthService;
import com.synrgybootcamp.project.service.impl.AuthServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.util.ErrorResponse;
import com.synrgybootcamp.project.web.model.request.ChangePasswordRequest;
import com.synrgybootcamp.project.web.model.request.ForgotPasswordRequest;
import com.synrgybootcamp.project.web.model.request.SignInRequest;
import com.synrgybootcamp.project.web.model.request.SignUpRequest;
import com.synrgybootcamp.project.web.model.response.SignInResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Api(tags = "Authentication", description = "Authentication Controller")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    GamificationHelper gamificationHelper;

    @PostMapping("signin")
    @ApiOperation(value = "Sign In User")
    public ApiResponse<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest
    ) {
        SignInResponse signInResponse = authService.signIn(signInRequest);

        return new ApiResponse<>("Sign In Success", signInResponse);
    }

    @PostMapping("signup")
    @ApiOperation(value = "Sign Up User (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponseWithoutData signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {

        User user = authService.signUp(signUpRequest);
        gamificationHelper.startGamificationForNewUser(user);

        return new ApiResponseWithoutData("Account Created");
    }

    @PostMapping("forgot-password")
    @ApiOperation(value = "Request change password for user")
    public ApiResponseWithoutData forgotPassword(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest
    ) {
        authService.forgotPassword(forgotPasswordRequest);

        return new ApiResponseWithoutData("Email Sended");
    }

    @PostMapping("change-password/{token}")
    @ApiOperation(value = "Check token validity and change user password")
    public ApiResponseWithoutData changePassword(
            @PathVariable String token,
            @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        authService.changePassword(changePasswordRequest, token);

        return new ApiResponseWithoutData("Password changed");
    }
}
