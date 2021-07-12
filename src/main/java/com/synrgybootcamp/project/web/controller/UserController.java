package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.enums.DateFilter;
import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.DashboardService;
import com.synrgybootcamp.project.service.UserBalanceService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.UserBalanceResponse;
import com.synrgybootcamp.project.web.model.response.UserGamificationStatusResponse;
import com.synrgybootcamp.project.web.model.response.WebDashboardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "User", description = "User Controller")
public class UserController {

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private GamificationHelper gamificationHelper;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("api/user/balance")
    @ApiOperation(value = "Get user balance")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<UserBalanceResponse> getUserBalance() {
        UserBalanceResponse balance = userBalanceService.getUserBalance(userInformation.getUserID());

        return new ApiResponse<>("successfully get user balance", balance);

    }

    @GetMapping("api/user/web")
    @ApiOperation(value = "Get user information for web dashboard")
    public ApiResponse<WebDashboardResponse> getUserDashboardWebInformation(@RequestParam(name = "graph_filter") DateFilter dateFilter) {
        WebDashboardResponse res = dashboardService.webDashboard(dateFilter);

        return new ApiResponse<>("successfully get user balance", res);

    }

    @GetMapping("api/gamification/me")
    @ApiOperation(value = "Get user gamification status")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<UserGamificationStatusResponse> getUserGamificationStatus() {
        UserGamificationStatusResponse gamificationStatusResponse = gamificationHelper.getUserPlanetGamificationStatus();

        return new ApiResponse<>("successfully get user gamification status", gamificationStatusResponse);
    }
}
