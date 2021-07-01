package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PaymentMerchantServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MerchantRequest;
import com.synrgybootcamp.project.web.model.response.MerchantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/merchant")
@Api(tags = "Merchant", description = "Merchant Controller")
public class MerchantController {

    @Autowired
    private PaymentMerchantServiceImpl merchantService;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @GetMapping("")
    @ApiOperation(value = "Get list of merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> listMerchant() {
        List<MerchantResponse> merchantResponses  = merchantService.getAllMerchants();
        return new ResponseEntity<>(
                new ApiResponse("success get all merchants", merchantResponses), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of merchant")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getMerchantById(
            @PathVariable String id){
        MerchantResponse merchantResponse = merchantService.getMerchantById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail merchant", merchantResponse), HttpStatus.OK
        );
    }

    @PostMapping("")
    @ApiOperation(value = "Add new merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addMerchant(@ModelAttribute MerchantRequest merchantRequest){
        MerchantResponse merchantResponse = merchantService.addMerchant(merchantRequest);

        return new ResponseEntity<>(
                new ApiResponse("success add new merchant", merchantResponse), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateMerchantById(
            @PathVariable String id,
            @ModelAttribute MerchantRequest merchantRequest){
        MerchantResponse merchantResponse = merchantService.updateMerchantById(id, merchantRequest);

        return new ResponseEntity<>(
                new ApiResponse("success edit merchant", merchantResponse), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteMerchantById(@PathVariable String id){
        boolean deleted = merchantService.deleteMerchantById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete merchant" : "merchant not found"), HttpStatus.OK
        );
    }
}
