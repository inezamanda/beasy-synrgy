package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.FaqService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.FaqRequest;
import com.synrgybootcamp.project.web.model.response.FaqResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/faq")
@Api(tags = "FAQ", description = "FAQ Controller")
public class FaqController {
    @Autowired
    private FaqService faqService;

    @GetMapping()
    @ApiOperation(value = "Get list of FAQ")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<FaqResponse>> getAllFaqs() {
        List<FaqResponse> faqResponse = faqService.getAllFaqs();
        return new ApiResponse<>("Successfuly get All FAQs", faqResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get FAQ by id")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<FaqResponse> getFaqById(@PathVariable String id) {
        FaqResponse faqResponse = faqService.getFaqByID(id);
        return new ApiResponse<>("Successfuly get FAQ", faqResponse);
    }

    @PostMapping()
    @ApiOperation(value = "Add new FAQ (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<FaqResponse> addNewFaq(@RequestBody FaqRequest faqRequest) {
        FaqResponse faqResponse = faqService.addNewFaq(faqRequest);
        return new ApiResponse<>("Succesfully add FAQ", faqResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit FAQ (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<FaqResponse> editFaqById(@PathVariable String id, @RequestBody FaqRequest faqRequest) {
        FaqResponse faqResponse = faqService.editFaq(id, faqRequest);
        return new ApiResponse<>("Successfuly edit FAQ", faqResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete FAQ (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<FaqResponse> deleteFaqById(@PathVariable String id) {
        FaqResponse faqResponse = faqService.deleteFaq(id);
        return new ApiResponse<>("Successfuly delete FAQ", faqResponse);
    }
}
