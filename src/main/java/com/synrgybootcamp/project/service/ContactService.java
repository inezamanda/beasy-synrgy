package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;

public interface ContactService {
    ContactResponse createContact(ContactRequest contactRequest);
    ContactResponse getContactById(String id);
    ContactResponse updateContactById(String id, ContactRequest contactRequest);
    boolean deleteContactById(String id);
}
