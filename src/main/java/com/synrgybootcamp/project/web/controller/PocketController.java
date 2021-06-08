package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pocket")
public class PocketController {

    @Autowired
    PocketService pocketService;

    @Autowired
    private UserInformation userInformation;

    @PostMapping("{pocketId}/topup")
    public ResponseEntity<Object> topUpPocket(
            @PathVariable String pocketId,
            @RequestBody TopUpPocketBalanceRequest payload
    ) {
        TopUpPocketBalanceResponse topUpResult = pocketService
                .topUpBalance(
                        userInformation.getUserID(),
                        TopUpPocketBalanceRequest
                                .builder()
                                .destination(pocketId)
                                .amount(payload.getAmount())
                                .build()
                );

        return new ResponseEntity<>(
                new ApiResponse("Top up pocket success", topUpResult)
                , HttpStatus.OK
        );
    }

    @PostMapping("{pocketId}/move")
    public ResponseEntity<Object> movePocketBalance(
            @PathVariable String pocketId,
            @RequestBody MovePocketBalanceRequest payload
    ) {
        MovePocketBalanceResponse moveBalanceResult = pocketService
                .moveBalance(
                        MovePocketBalanceRequest.builder()
                                .amount(payload.getAmount())
                                .source(pocketId)
                                .destination(payload.getDestination())
                                .build()
                );

        return new ResponseEntity<>(
                new ApiResponse("Move pocket balance success", moveBalanceResult)
                , HttpStatus.OK
        );
    }
}
