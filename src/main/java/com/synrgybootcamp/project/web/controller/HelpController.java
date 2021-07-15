package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.HelpServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.HelpRequest;
import com.synrgybootcamp.project.web.model.response.HelpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/help")
@Api(tags = "Help", description = "Help Controller")
public class HelpController {
    @Autowired
    private HelpServiceImpl helpService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get list of help request (Admin Only)")
    public ApiResponse<List<HelpResponse>> getAllHelp() {
        List<HelpResponse> helps = helpService.getAllHelp();
        return new ApiResponse<>("Successfully get All Help Request", helps);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get help request (Admin Only)")
    public ApiResponse<HelpResponse> getHelpByID (@PathVariable String id) {
        HelpResponse help = helpService.getHelpById(id);
        return new ApiResponse<>("Successfully get Help Request", help);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Add new Help")
    public ApiResponse<HelpResponse> addNewHelp (@RequestBody HelpRequest helpRequest) {
        HelpResponse help = helpService.addNewHelp(helpRequest);
        return new ApiResponse<>("Request for help has been sent", help);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get help request (Admin Only)")
    public ApiResponse<HelpResponse> deleteHelpById (@PathVariable String id) {
        HelpResponse help = helpService.deleteHelp(id);
        return new ApiResponse<>("Successfully delete Help Request", help);
    }
}
