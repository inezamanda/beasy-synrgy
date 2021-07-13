package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.ProfileRequest;
import com.synrgybootcamp.project.web.model.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse getUserProfile();
    ProfileResponse editUserProfile(ProfileRequest profileRequest);
}
