package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.Bank;
import com.synrgybootcamp.project.entity.Contact;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.repository.BankRepository;
import com.synrgybootcamp.project.repository.ContactRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInformation userInformation;

    @Override
    public ContactResponse createContact(ContactRequest contactRequest) {
        Bank bank = bankRepository.findById(contactRequest.getBank_id())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Bank tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElse(null);

        Contact contact = contactRepository.save(
                Contact.builder()
                        .name(contactRequest.getName())
                        .accountNumber(contactRequest.getAccount_number())
                        .bank(bank)
                        .user(user)
                        .build()
        );

        return ContactResponse.builder()
                .name(contact.getName())
                .account_number(contact.getAccountNumber())
                .bank_id(bank.getId())
                .bank_name(bank.getName())
                .build();
    }

    @Override
    public ContactResponse getContactById(String id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        return contact == null
                ? null
                : ContactResponse
                .builder()
                .name(contact.getName())
                .account_number(contact.getAccountNumber())
                .bank_id(contact.getBank().getId())
                .bank_name(contact.getBank().getName())
                .cost(contact.getBank().getPrimary() ? 0 : TransactionConstants.DIFFERENT_BANK_FEE)
                .build();
    }

    @Override
    public ContactResponse updateContactById(String id, ContactRequest contactRequest) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        Bank bank = bankRepository.findById(contactRequest.getBank_id())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Bank tidak ditemukan"));

        contact.setName(contactRequest.getName());
        contact.setAccountNumber(contactRequest.getAccount_number());
        contact.setBank(bank);

        Contact contactResult = contactRepository.save(contact);

        return ContactResponse
                .builder()
                .name(contactResult.getName())
                .account_number(contactResult.getAccountNumber())
                .bank_id(bank.getId())
                .bank_name(bank.getName())
                .build();
    }

    @Override
    public boolean deleteContactById(String id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        contactRepository.delete(contact);
        return true;
    }
}
