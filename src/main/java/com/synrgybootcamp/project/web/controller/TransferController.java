package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.TransferService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping()
    public ResponseEntity<Object> transfer(@RequestBody TransferRequest transferRequest) {
        TransferResponse transferResult = transferService.transfer(
                TransferRequest
                        .builder()
                        .contact_id(transferRequest.getContact_id())
                        .amount(transferRequest.getAmount())
                        .note(transferRequest.getNote())
                        .pin(transferRequest.getPin())
                        .build()
        );

        return new ResponseEntity<>(
                new ApiResponse("Transfer success", transferResult)
                , HttpStatus.OK
        );
    }
}
