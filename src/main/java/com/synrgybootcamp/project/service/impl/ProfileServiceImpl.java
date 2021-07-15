package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.ProfileService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.DebitCardUtil;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MobileProfileRequest;
import com.synrgybootcamp.project.web.model.request.WebProfileRequest;
import com.synrgybootcamp.project.web.model.response.MobileProfileResponse;
import com.synrgybootcamp.project.web.model.response.MobileProfileSettingResponse;
import com.synrgybootcamp.project.web.model.response.WebProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    @Autowired
    private DebitCardUtil debitCardUtil;

    @Override
    public WebProfileResponse getUserProfileWeb() {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User Not Found"));
        return getProfileResponse(user);
    }

    @Override
    public WebProfileResponse editUserProfileWeb(WebProfileRequest webProfileRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));

        String profilePicture = "https://m.udustars.com/content/themes/default/images/blank_profile.jpg";

        if(Objects.nonNull(webProfileRequest.getProfilePicture())) {
            profilePicture = uploadFileUtil.upload(webProfileRequest.getProfilePicture());
        }

        if (Objects.nonNull(webProfileRequest.getCurrentPassword()) && Objects.nonNull(webProfileRequest.getNewPassword())) {
            if (!passwordEncoder.matches(webProfileRequest.getCurrentPassword(), user.getPassword())) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Wrong Password");
            }
            user.setPassword(passwordEncoder.encode(webProfileRequest.getNewPassword()));
        }

        user.setFullName(webProfileRequest.getFirstName() + " " + webProfileRequest.getLastName());
        user.setProfilePicture(profilePicture);
        user.setEmail(webProfileRequest.getEmail());
        user.setTelephone(webProfileRequest.getPhoneNumber());
        userRepository.save(user);

        return getProfileResponse(user);
    }

    @Override
    public MobileProfileResponse getUserProfileMobile() {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));
        if(user.getCardNumber() == null & user.getExpiryDate() == null) {
            String cardNumber = debitCardUtil.generate();
            Date expiryDate = debitCardUtil.expiryDate();
            user.setCardNumber(cardNumber);
            user.setExpiryDate(expiryDate);
            userRepository.save(user);
        }
        return MobileProfileResponse
                .builder()
                .profilePicture(user.getProfilePicture())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getTelephone())
                .cardNumber(user.getCardNumber())
                .build();
    }

    @Override
    public MobileProfileSettingResponse getUserProfileSettingMobile() {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));
        return MobileProfileSettingResponse
                .builder()
                .pin(user.getPin())
                .email(user.getEmail())
                .build();
    }

    @Override
    public MobileProfileSettingResponse editUserProfileSettingMobile(MobileProfileRequest mobileProfileRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));
        user.setPin(mobileProfileRequest.getPin());
        user.setEmail(mobileProfileRequest.getEmail());
        userRepository.save(user);
        return MobileProfileSettingResponse
                .builder()
                .pin(user.getPin())
                .email(user.getEmail())
                .build();
    }

    private WebProfileResponse getProfileResponse(User user) {
        String fullName = user.getFullName();
        String firstName = "";
        String lastName = "";
        if(fullName.split(" ").length > 1){
            lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);
            firstName = fullName.substring(0, fullName.lastIndexOf(" "));
        } else {
            firstName = fullName;
        }
        return WebProfileResponse
                .builder()
                .profilePicture(user.getProfilePicture())
                .firstName(firstName)
                .lastName(lastName)
                .email(user.getEmail())
                .phoneNumber(user.getTelephone())
                .build();
    }
}
