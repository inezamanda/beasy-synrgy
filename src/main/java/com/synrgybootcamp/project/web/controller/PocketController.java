package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.impl.PocketServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pocket")
@Api(tags = "Pocket", description = "Pocket Controller")
public class PocketController {
    @Autowired
    private PocketServiceImpl pocketService;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @GetMapping("")
    @ApiOperation(value = "Get list of pockets")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllPockets(){

        List<PocketResponse> pocketResponse = pocketService.getAllPocket();

        return new ResponseEntity<>(
                new ApiResponse("successfully get pocket user", pocketResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    @ApiOperation(value = "Create new pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createPocket(
            @ModelAttribute PocketRequest pocketRequest
    ){
        PocketResponse createPocket = pocketService.createPocket(pocketRequest);

        return new ResponseEntity<>(
                new ApiResponse("success create pocket data", createPocket),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getPocketsByID(@PathVariable String id){
        PocketResponse detailPocket = pocketService.getDetailPocketByID(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail pocket data", detailPocket),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/history")
    @ApiOperation(value = "Get history of pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getHistoryPockets(
            @PathVariable String id,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder
    ){
        List<PocketTransactionResponse> transactionResponses = pocketService.getHistory(
                id,
                sortOrder.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        return new ResponseEntity<>(
                new ApiResponse("successfully get history pocket", transactionResponses),
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete pocket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deletePocketsByID(@PathVariable String id){
       boolean pocketDelete = pocketService.deletePocketById(id);

        return new ResponseEntity<>(
                new ApiResponse(pocketDelete ?"success delete pocket data" : "data pocket tidak ditemukan" ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePocketByID(
            @PathVariable String id,
            @ModelAttribute PocketRequest pocketRequest){

       PocketResponse editPocket = pocketService.updatePocketById(id,pocketRequest);

        return new ResponseEntity<>(
                new ApiResponse("success edit pocket data",editPocket),HttpStatus.OK
        );
    }

    @PostMapping("{id}/topup")
    public ResponseEntity<Object> topUpPocket(
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

        return new ResponseEntity<>(
                new ApiResponse("Top up pocket success", topUpResult)
                , HttpStatus.OK
        );
    }

    @PostMapping("{id}/move")
    public ResponseEntity<Object> movePocketBalance(
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

        return new ResponseEntity<>(
                new ApiResponse("Move pocket balance success", moveBalanceResult)
                , HttpStatus.OK
        );
    }
}
