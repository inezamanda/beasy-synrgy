package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.ContactServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {
    @Autowired
    ContactServiceImpl contactService;

    @GetMapping
    public ResponseEntity<ApiResponse> listContact(
            @RequestParam(required = false) String keyword
    ) {
        List<ContactResponse> contacts = contactService.getAllContacts(keyword);
        return new ResponseEntity<>(
                new ApiResponse("success get all contacts", contacts), HttpStatus.OK
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> recentContacts(){
        List<ContactResponse> contacts = contactService.recentContacts();
        return new ResponseEntity<>(
                new ApiResponse("success get all contacts", contacts), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addContact(@RequestBody ContactRequest contactRequest){
        ContactResponse contact = contactService.createContact(contactRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new contact", contact), HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getContactById(
            @PathVariable String id){
        ContactResponse detailContact = contactService.getContactById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail contact", detailContact), HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateContactById(
            @PathVariable String id,
            @RequestBody ContactRequest contactRequest){
        ContactResponse contact = contactService.updateContactById(id, contactRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit contact", contact), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteContactById(@PathVariable String id){
        boolean deleted = contactService.deleteContactById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete contact" : "contact not found"), HttpStatus.OK
        );
    }
}
