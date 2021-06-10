package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Bank;
import com.synrgybootcamp.project.entity.Contact;
import com.synrgybootcamp.project.repository.BankRepository;
import com.synrgybootcamp.project.repository.ContactRepository;
import com.synrgybootcamp.project.service.ContactService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    BankRepository bankRepository;

    @Override
    public ContactResponse createContact(ContactRequest contactRequest) {
        Bank bank = bankRepository.findById(contactRequest.getBank_id())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Bank tidak ditemukan"));

        Contact contact = contactRepository.save(
                Contact.builder()
                        .name(contactRequest.getName())
                        .accountNumber(contactRequest.getAccount_number())
                        .bank(bank)
                        .build()
        );

        return ContactResponse.builder()
                .name(contact.getName())
                .account_number(contact.getAccountNumber())
                .bank_id(bank.getId())
                .bank_name(bank.getName())
                .build();
    }
}
