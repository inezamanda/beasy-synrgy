package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.ProfileService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.ProfileRequest;
import com.synrgybootcamp.project.web.model.response.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public ProfileResponse getUserProfile() {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User Not Found"));
        return getProfileResponse(user);
    }

    @Override
    public ProfileResponse editUserProfile(ProfileRequest profileRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));

        String profilePicture = "https://m.udustars.com/content/themes/default/images/blank_profile.jpg";

        if(Objects.nonNull(profileRequest.getProfilePicture())) {
            profilePicture = uploadFileUtil.upload(profileRequest.getProfilePicture());
        }

        if (Objects.nonNull(profileRequest.getCurrentPassword()) && Objects.nonNull(profileRequest.getNewPassword())) {
            if (!passwordEncoder.matches(profileRequest.getCurrentPassword(), user.getPassword())) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Wrong Password");
            }
            user.setPassword(passwordEncoder.encode(profileRequest.getNewPassword()));
        }

        user.setFullName(profileRequest.getFirstName() + " " + profileRequest.getLastName());
        user.setProfilePicture(profilePicture);
        user.setEmail(profileRequest.getEmail());
        user.setTelephone(profileRequest.getPhoneNumber());
        userRepository.save(user);

        return getProfileResponse(user);
    }

    private ProfileResponse getProfileResponse(User user) {
        String fullName = user.getFullName();
        String firstName = "";
        String lastName = "";
        if(fullName.split(" ").length > 1){
            lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);
            firstName = fullName.substring(0, fullName.lastIndexOf(" "));
        } else {
            firstName = fullName;
        }
        return ProfileResponse
                .builder()
                .profilePicture(user.getProfilePicture())
                .firstName(firstName)
                .lastName(lastName)
                .email(user.getEmail())
                .phoneNumber(user.getTelephone())
                .build();
    }
}
