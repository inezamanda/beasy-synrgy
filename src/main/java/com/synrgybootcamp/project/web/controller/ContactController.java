package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.ContactServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/contacts")
public class ContactController {
    @Autowired
    ContactServiceImpl contactService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addContact(@RequestBody ContactRequest contactRequest){
        ContactResponse contact = contactService.createContact(contactRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new contact", contact), HttpStatus.OK
        );
    }
}
