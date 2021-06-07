package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.web.model.request.SignInRequest;
import com.synrgybootcamp.project.web.model.request.SignUpRequest;
import com.synrgybootcamp.project.web.model.response.SignInResponse;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);
    User signUp(SignUpRequest signUpRequest);
}
