package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.ContactService;
import com.synrgybootcamp.project.service.impl.ContactServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
@Api(tags = "Contact", description = "Contact Controller")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping
    @ApiOperation(value = "Get list of contact")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<ContactResponse>> listContact(
            @RequestParam(required = false) String keyword
    ) {
        List<ContactResponse> contacts = contactService.getAllContacts(keyword);

        return new ApiResponse<>("success get all contacts", contacts);
    }

    @GetMapping("/recent")
    @ApiOperation(value = "Get list of recent contact")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<ContactResponse>> recentContacts(){
        List<ContactResponse> contacts = contactService.recentContacts();

        return new ApiResponse<>("success get all contacts", contacts);
    }

    @PostMapping
    public ApiResponse<ContactResponse> addContact(@RequestBody ContactRequest contactRequest){
        ContactResponse contact = contactService.createContact(contactRequest);

        return new ApiResponse<>("success add new contact", contact);
    }

    @GetMapping("/{id}")
    public ApiResponse<ContactResponse> getContactById(
            @PathVariable String id
    ){
        ContactResponse detailContact = contactService.getContactById(id);

        return new ApiResponse<>("success get detail contact", detailContact);
    }

    @PutMapping("/{id}")
    public ApiResponse<ContactResponse> updateContactById(
            @PathVariable String id,
            @RequestBody ContactRequest contactRequest
    ){
        ContactResponse contact = contactService.updateContactById(id, contactRequest);

        return new ApiResponse<>("success edit contact", contact);
    }

    @DeleteMapping("/{id}")
    public ApiResponseWithoutData deleteContactById(@PathVariable String id){
        contactService.deleteContactById(id);

        return new ApiResponseWithoutData("contact not found");
    }
}
