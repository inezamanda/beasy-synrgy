package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.web.model.request.ChangePasswordRequest;
import com.synrgybootcamp.project.web.model.request.ForgotPasswordRequest;
import com.synrgybootcamp.project.web.model.request.SignInRequest;
import com.synrgybootcamp.project.web.model.request.SignUpRequest;
import com.synrgybootcamp.project.web.model.response.SignInResponse;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);
    User signUp(SignUpRequest signUpRequest);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    void changePassword(ChangePasswordRequest changePasswordRequest, String token);
}
