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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
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
    public List<ContactResponse> getAllContacts(String keyword) {
        List<Contact> contacts;

        User loggedInUser = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (keyword == null) {
            contacts = contactRepository.findByUser(loggedInUser);
        } else {
            List<Contact> contactsResultByName = contactRepository.
                    findByNameIsContainingIgnoreCaseAndUser(keyword, loggedInUser);

            List<String> ids = Optional.ofNullable(contactsResultByName)
                    .filter(val -> !CollectionUtils.isEmpty(val))
                    .map(data -> data.stream().map(Contact::getId).collect(Collectors.toList()))
                    .orElse(Arrays.asList("-"));

            List<Contact> contactsResultByAccountNumber = contactRepository
                    .findByAccountNumberContainingIgnoreCaseAndUserAndIdNotIn(keyword, loggedInUser, ids);

            contacts = Stream
                    .concat(contactsResultByName.stream(), contactsResultByAccountNumber.stream())
                    .collect(Collectors.toList());
        }

        return contacts
                .stream()
                .map(contact -> ContactResponse
                        .builder()
                        .name(contact.getName())
                        .account_number(contact.getAccountNumber())
                        .bank_id(contact.getBank().getId())
                        .bank_name(contact.getBank().getName())
                        .cost(contact.getBank().getPrimary() ? 0 : TransactionConstants.DIFFERENT_BANK_FEE)
                        .build()
                ).collect(Collectors.toList());
    }

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
