package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.enums.PocketTransactionType;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import com.synrgybootcamp.project.web.model.response.PocketTransactionResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/pocket")
@Api(tags = "Pocket", description = "Pocket Controller")
public class PocketController {
    @Autowired
    private PocketService pocketService;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @GetMapping("")
    @ApiOperation(value = "Get list of pockets")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<PocketResponse>> getAllPockets(){

        List<PocketResponse> pocketResponse = pocketService.getAllPocket();

        return new ApiResponse<>("successfully get pocket user", pocketResponse);
    }

    @PostMapping("")
    @ApiOperation(value = "Create new pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<PocketResponse> createPocket( @ModelAttribute PocketRequest pocketRequest ){

        PocketResponse createPocket = pocketService.createPocket(pocketRequest);

        return new ApiResponse<>("success create pocket data", createPocket);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<PocketResponse> getPocketsByID(@PathVariable String id){
        PocketResponse detailPocket = pocketService.getDetailPocketByID(id);

        return new ApiResponse<>("success get detail pocket data", detailPocket);
    }

    @GetMapping("/{id}/history")
    @ApiOperation(value = "Get history of pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<PocketTransactionResponse>> getHistoryPockets(@PathVariable String id,
        @RequestParam(name = "sort_by", defaultValue = "id") String sortBy,
        @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
        @RequestParam(name= "type", required = false) PocketTransactionType pocketTransactionType
    ){
        List<PocketTransactionResponse> transactionResponses = pocketService.getHistory(
            id,
            sortOrder.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending(),
            pocketTransactionType
        );

        return new ApiResponse<>("successfully get history pocket", transactionResponses);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponseWithoutData deletePocketsByID(@PathVariable String id){
       pocketService.deletePocketById(id);

        return new ApiResponseWithoutData("success delete pocket data");
    }

    @PutMapping("/{id}")
    public ApiResponse<PocketResponse> updatePocketByID(
            @PathVariable String id,
            @ModelAttribute PocketRequest pocketRequest){

       PocketResponse editPocket = pocketService.updatePocketById(id,pocketRequest);

        return new ApiResponse<>("success edit pocket data",editPocket);
    }

    @PostMapping("{id}/topup")
    public ApiResponse<TopUpPocketBalanceResponse> topUpPocket(
            @PathVariable String id,
            @RequestBody TopUpPocketBalanceRequest payload
    ) {
        TopUpPocketBalanceResponse topUpResult = pocketService
                .topUpBalance(
                        userInformation.getUserID(),
                        TopUpPocketBalanceRequest
                                .builder()
                                .destination(id)
                                .amount(payload.getAmount())
                                .build()
                );

        return new ApiResponse<>("Top up pocket success", topUpResult);
    }

    @PostMapping("{id}/move")
    public ApiResponse<MovePocketBalanceResponse> movePocketBalance(
            @PathVariable String id,
            @RequestBody MovePocketBalanceRequest payload
    ) {
        MovePocketBalanceResponse moveBalanceResult = pocketService
                .moveBalance(
                        MovePocketBalanceRequest.builder()
                                .amount(payload.getAmount())
                                .source(id)
                                .destination(payload.getDestination())
                                .build()
                );

        return new ApiResponse<>("Move pocket balance success", moveBalanceResult);
    }
}
