package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MobileProfileRequest;
import com.synrgybootcamp.project.web.model.request.WebProfileRequest;
import com.synrgybootcamp.project.web.model.response.MobileProfileResponse;
import com.synrgybootcamp.project.web.model.response.MobileProfileSettingResponse;
import com.synrgybootcamp.project.web.model.response.WebProfileResponse;

public interface ProfileService {
    WebProfileResponse getUserProfileWeb();
    WebProfileResponse editUserProfileWeb(WebProfileRequest webProfileRequest);
    MobileProfileResponse getUserProfileMobile();
    MobileProfileSettingResponse getUserProfileSettingMobile();
    MobileProfileSettingResponse editUserProfileSettingMobile(MobileProfileRequest mobileProfileRequest);
}
