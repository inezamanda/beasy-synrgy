package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.TransferService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferHistoryResponse;
import com.synrgybootcamp.project.web.model.response.TransferResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                        .contactId(transferRequest.getContactId())
                        .amount(transferRequest.getAmount())
                        .note(transferRequest.getNote())
                        .pin(transferRequest.getPin())
                        .build()
        );

        return new ApiResponse<>("Transfer success", transferResult);
    }

    @GetMapping("/history")
    @ApiOperation(value = "Get history of transfer")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<TransferHistoryResponse>> getHistoryTransfers(
            @RequestParam(name = "sort_by", defaultValue = "date") String sortBy,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder
    ){
        List<TransferHistoryResponse> transferResponses = transferService.getHistory(
                sortOrder.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        return new ApiResponse<>("successfully get transfer history", transferResponses);
    }
}
