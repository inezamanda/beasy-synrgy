package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.PaymentMerchantService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MerchantRequest;
import com.synrgybootcamp.project.web.model.response.MerchantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/merchant")
@Api(tags = "Merchant", description = "Merchant Controller")
public class MerchantController {

    @Autowired
    private PaymentMerchantService merchantService;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @GetMapping("")
    @ApiOperation(value = "Get list of merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<MerchantResponse>> listMerchant() {
        List<MerchantResponse> merchantResponses  = merchantService.getAllMerchants();

        return new ApiResponse<>("success get all merchants", merchantResponses);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of merchant")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<MerchantResponse> getMerchantById(@PathVariable String id){
        MerchantResponse merchantResponse = merchantService.getMerchantById(id);

        return new ApiResponse<>("success get detail merchant", merchantResponse);
    }

    @PostMapping("")
    @ApiOperation(value = "Add new merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<MerchantResponse> addMerchant(@ModelAttribute MerchantRequest merchantRequest){
        MerchantResponse merchantResponse = merchantService.addMerchant(merchantRequest);

        return new ApiResponse<>("success add new merchant", merchantResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<MerchantResponse> updateMerchantById(
            @PathVariable String id,
            @ModelAttribute MerchantRequest merchantRequest
    ){
        MerchantResponse merchantResponse = merchantService.updateMerchantById(id, merchantRequest);

        return new ApiResponse<>("success edit merchant", merchantResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete merchant (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponseWithoutData deleteMerchantById(@PathVariable String id){
        merchantService.deleteMerchantById(id);

        return new ApiResponseWithoutData("success delete merchant");
    }
}
