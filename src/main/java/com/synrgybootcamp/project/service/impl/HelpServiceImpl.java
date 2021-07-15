package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Help;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.repository.HelpRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.HelpService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.EmailSender;
import com.synrgybootcamp.project.web.model.request.HelpRequest;
import com.synrgybootcamp.project.web.model.response.HelpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpServiceImpl implements HelpService {
    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public List<HelpResponse> getAllHelp() {
        List<Help> helps  = helpRepository.findAll();
        return helps.stream()
                .map(help -> HelpResponse
                        .builder()
                        .id(help.getId())
                        .name(help.getName())
                        .message(help.getMessage())
                        .phoneNumber(help.getPhoneNumber())
                        .email(help.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public HelpResponse getHelpById(String id) {
        Help help = helpRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Help Not Found"));
        return HelpResponse
                .builder()
                .id(help.getId())
                .name(help.getName())
                .message(help.getMessage())
                .phoneNumber(help.getPhoneNumber())
                .email(help.getEmail())
                .build();
    }

    @Override
    public HelpResponse addNewHelp(HelpRequest helpRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User Not Found"));
        Help help = helpRepository.save(
                Help
                        .builder()
                        .name(helpRequest.getName())
                        .message(helpRequest.getMessage())
                        .phoneNumber(helpRequest.getPhoneNumber())
                        .email(helpRequest.getEmail())
                        .user(user)
                    .build()
        );

        String to = user.getEmail();
        String subject = "Help Request from " + user.getEmail();
        String message = "<pre>Hello, " + help.getName() +
                         "<br />Thank you for contacting us.<br /><br /><br />" +
                         "ID : " + help.getId() +
                         "<br />Your help request : "+ help.getMessage() +
                         "<br /><br />We will respond to your request for help immediately. Thank you. " +
                         "<br />Regards, Beasy Support</pre>";
        emailSender.sendMail(to, subject, message);

        return HelpResponse
                .builder()
                .id(help.getId())
                .name(help.getName())
                .message(help.getMessage())
                .phoneNumber(help.getPhoneNumber())
                .email(help.getEmail())
                .build();
    }

    @Override
    public HelpResponse deleteHelp(String id) {
        Help help = helpRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Help Not Found"));
        helpRepository.delete(help);
        return HelpResponse
                .builder()
                .id(help.getId())
                .name(help.getName())
                .message(help.getMessage())
                .phoneNumber(help.getPhoneNumber())
                .email(help.getEmail())
                .build();
    }
}
