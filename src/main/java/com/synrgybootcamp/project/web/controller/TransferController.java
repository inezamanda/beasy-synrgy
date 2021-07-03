package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.TransferService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transfer")
@Api(tags = "Transfer", description = "Transfer Controller")
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping()
    @ApiOperation(value = "Transfer money from main pocket a.k.a balance")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<TransferResponse> transfer(@RequestBody TransferRequest transferRequest) {
        TransferResponse transferResult = transferService.transfer(
                TransferRequest
                        .builder()
                        .contact_id(transferRequest.getContact_id())
                        .amount(transferRequest.getAmount())
                        .note(transferRequest.getNote())
                        .pin(transferRequest.getPin())
                        .build()
        );

        return new ApiResponse<>("Transfer success", transferResult);
    }
}
