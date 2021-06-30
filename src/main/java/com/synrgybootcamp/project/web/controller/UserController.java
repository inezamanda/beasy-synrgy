package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.UserBalanceService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.ExampleResponse;
import com.synrgybootcamp.project.web.model.response.UserBalanceResponse;
import com.synrgybootcamp.project.web.model.response.UserGamificationStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private GamificationHelper gamificationHelper;

    @GetMapping("api/user/balance")
    public ResponseEntity<ApiResponse> getUserBalance() {
        UserBalanceResponse balance = userBalanceService.getUserBalance(userInformation.getUserID());

        return new ResponseEntity<>(
                new ApiResponse("successfully get user balance", balance),
                HttpStatus.OK
        );

    }

    @GetMapping("api/gamification/me")
    public ResponseEntity<ApiResponse> getUserGamificationStatus() {
        UserGamificationStatusResponse gamificationStatusResponse = gamificationHelper.getUserPlanetGamificationStatus();

        return new ResponseEntity<>(
            new ApiResponse("successfully get user gamification status", gamificationStatusResponse),
            HttpStatus.OK
        );
    }
}
