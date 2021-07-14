package com.synrgybootcamp.project.helper;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.Contact;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactHelper {

    public static List<Contact> fetchRecentContact(List<Contact> contacts) {
        List<Contact> recentContacts = new ArrayList<>();

        contacts.stream().forEach(contact -> {
            if (recentContacts.size() < 3) {
                if (!CollectionUtils.contains(recentContacts.iterator(), contact)) {
                    recentContacts.add(contact);
                }
            }
        });

        return recentContacts;
    }

    public static List<ContactResponse> toWebResponse(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> ContactResponse
                        .builder()
                        .id(contact.getId())
                        .name(contact.getName())
                        .accountNumber(contact.getAccountNumber())
                        .bankId(contact.getBank().getId())
                        .bankName(contact.getBank().getName())
                        .cost(contact.getBank().getPrimary() ? 0 : TransactionConstants.DIFFERENT_BANK_FEE)
                        .build()
                ).collect(Collectors.toList());
    }

    public static List<Contact> getFromTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransfer().getContact() != null)
                .map(transaction -> transaction.getTransfer().getContact())
                .collect(Collectors.toList());
    }
}
