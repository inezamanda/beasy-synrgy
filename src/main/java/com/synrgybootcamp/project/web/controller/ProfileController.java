package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.ProfileServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.ProfileRequest;
import com.synrgybootcamp.project.web.model.response.ProfileResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@Api(tags = "Profile", description = "Profile Controller")
public class ProfileController {

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/web")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get user profile (Web)")
    public ApiResponse<ProfileResponse> getUserProfile() {
        ProfileResponse profileResponse = profileService.getUserProfile();
        return new ApiResponse<>("Successfully get User Profile", profileResponse);
    }

    @PutMapping("/web")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Edit user profile (Web)")
    public ApiResponse<ProfileResponse> editUserProfile(@ModelAttribute ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.editUserProfile(profileRequest);

        return new ApiResponse<>("Successfuly change User Profile", profileResponse);
    }
}
