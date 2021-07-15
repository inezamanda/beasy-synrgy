package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.entity.Help;
import com.synrgybootcamp.project.web.model.request.HelpRequest;
import com.synrgybootcamp.project.web.model.response.HelpResponse;

import java.util.List;

public interface HelpService {
    List<HelpResponse> getAllHelp();
    HelpResponse getHelpById(String id);
    HelpResponse addNewHelp(HelpRequest helpRequest);
    HelpResponse deleteHelp(String id);
}
