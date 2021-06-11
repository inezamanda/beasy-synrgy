package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;

import java.util.List;

public interface ContactService {
    List<ContactResponse> getAllContacts(String keyword);
    ContactResponse createContact(ContactRequest contactRequest);
    ContactResponse getContactById(String id);
    ContactResponse updateContactById(String id, ContactRequest contactRequest);
    boolean deleteContactById(String id);
}
