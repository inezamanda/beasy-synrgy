package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.impl.PocketServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.UserBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/pocket")
public class PocketController {
    @Autowired
    private PocketServiceImpl pocketService;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllPockets(){

        List<PocketResponse> pocketResponse = pocketService.getAllPocket(userInformation.getUserID());

        return new ResponseEntity<>(
                new ApiResponse("successfully get pocket user", pocketResponse),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getPocketsByID(@PathVariable String id){
        PocketResponse detailPocket = pocketService.getDetailPocketByID(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail pocket data", detailPocket),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deletePocketsByID(@PathVariable String id){
       boolean pocketDelete = pocketService.deletePocketById(id);

        return new ResponseEntity<>(
                new ApiResponse(pocketDelete ?"success delete pocket data" : "data pocket tidak ditemukan" ),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createPocket(@ModelAttribute PocketRequest pocketRequest,@RequestParam(value = "picture") MultipartFile picture){
        PocketResponse createPocket = pocketService.createPocket(pocketRequest);
//        uploadFileUtil.upload(picture);

        return new ResponseEntity<>(
                new ApiResponse("success create pocket data", createPocket),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updatePocketByID(@PathVariable String id,@RequestBody PocketRequest pocketRequest){
       PocketResponse editPocket = pocketService.updatePocketById(id,pocketRequest);

        return new ResponseEntity<>(
                new ApiResponse("success edit pocket data",editPocket),HttpStatus.OK
        );
    }



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
