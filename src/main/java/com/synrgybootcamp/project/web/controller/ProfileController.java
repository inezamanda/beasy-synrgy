package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.ProfileServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.MobileProfileRequest;
import com.synrgybootcamp.project.web.model.request.WebProfileRequest;
import com.synrgybootcamp.project.web.model.response.MobileProfileResponse;
import com.synrgybootcamp.project.web.model.response.MobileProfileSettingResponse;
import com.synrgybootcamp.project.web.model.response.WebProfileResponse;
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
    public ApiResponse<WebProfileResponse> getUserProfileWeb() {
        WebProfileResponse webProfileResponse = profileService.getUserProfileWeb();
        return new ApiResponse<>("Successfully get User Profile", webProfileResponse);
    }

    @PutMapping("/web")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Edit user profile (Web)")
    public ApiResponse<WebProfileResponse> editUserProfileWeb(@ModelAttribute WebProfileRequest webProfileRequest) {
        WebProfileResponse webProfileResponse = profileService.editUserProfileWeb(webProfileRequest);

        return new ApiResponse<>("Successfuly change User Profile", webProfileResponse);
    }

    @GetMapping("/mobile")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get user profile (Mobile)")
    public ApiResponse<MobileProfileResponse> getUserProfileMobile() {
        MobileProfileResponse mobileProfileResponse = profileService.getUserProfileMobile();
        return new ApiResponse<>("Successfully get User Profile", mobileProfileResponse);
    }

    @GetMapping("/mobile/setting")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get user profile setting (Mobile)")
    public ApiResponse<MobileProfileSettingResponse> getUserProfileSettingMobile() {
        MobileProfileSettingResponse mobileProfileSettingResponse = profileService.getUserProfileSettingMobile();
        return new ApiResponse<>("Successfully get User Profile Setting", mobileProfileSettingResponse);
    }

    @PutMapping("/mobile/setting")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Edit user profile setting (Mobile)")
    public ApiResponse<MobileProfileSettingResponse> editUserProfileWeb(@RequestBody MobileProfileRequest mobileProfileRequest) {
        MobileProfileSettingResponse mobileProfileSettingResponse = profileService.editUserProfileSettingMobile(mobileProfileRequest);

        return new ApiResponse<>("Successfuly change User Profile", mobileProfileSettingResponse);
    }
}
