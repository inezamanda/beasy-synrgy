package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.*;
import com.synrgybootcamp.project.enums.RewardPlanetType;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.*;
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
    UserRewardRepository userRewardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserInformation userInformation;

    @Override
    public List<ContactResponse> getAllContacts(String keyword) {
        List<Contact> contacts;

        User loggedInUser = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (keyword == null) {
            contacts = contactRepository.findByUserAndDeleteFalse(loggedInUser);
        } else {
            List<Contact> contactsResultByName = contactRepository.
                    findByNameIsContainingIgnoreCaseAndUserAndDeleteIsFalse(keyword, loggedInUser);

            List<String> ids = Optional.ofNullable(contactsResultByName)
                    .filter(val -> !CollectionUtils.isEmpty(val))
                    .map(data -> data.stream().map(Contact::getId).collect(Collectors.toList()))
                    .orElse(Arrays.asList("-"));

            List<Contact> contactsResultByAccountNumber = contactRepository
                    .findByAccountNumberContainingIgnoreCaseAndUserAndIdNotInAndDeleteIsFalse(keyword, loggedInUser, ids);

            contacts = Stream
                    .concat(contactsResultByName.stream(), contactsResultByAccountNumber.stream())
                    .collect(Collectors.toList());
        }

        return toListResponse(contacts);
    }

    @Override
    public List<ContactResponse> recentContacts() {

        User loggedInUser = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        return Optional.ofNullable(transactionRepository
                .findByUserAndTypeOrderByDateDesc(loggedInUser, TransactionType.TRANSFER))
                .map(this::getFromTransaction)
                .map(this::fetchRecentContact)
                .map(this::toListResponse)
                .orElse(new ArrayList<>());
    }

    @Override
    public ContactResponse createContact(ContactRequest contactRequest) {
        Bank bank = bankRepository.findById(contactRequest.getBankId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Bank tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (contactRepository.existsByUserAndNameAndDeleteIsFalseOrUserAndAccountNumberAndDeleteIsFalse(user, contactRequest.getName(), user, contactRequest.getAccountNumber())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is a contact with the same name / account number");
        }

        if (bank.getPrimary()) {
            if(!(userRepository.existsByAccountNumber(contactRequest.getAccountNumber()))) {
                throw new ApiException(HttpStatus.NOT_FOUND, "There is no beasy account with inputed account number registered");
            }
        }

        Contact contact = contactRepository.save(
                Contact.builder()
                        .name(contactRequest.getName())
                        .accountNumber(contactRequest.getAccountNumber())
                        .bank(bank)
                        .user(user)
                        .delete(false)
                        .build()
        );

        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .accountNumber(contact.getAccountNumber())
                .bankId(bank.getId())
                .bankName(bank.getName())
                .cost(getTransferCost(contact))
                .build();
    }

    @Override
    public ContactResponse getContactById(String id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        return ContactResponse
                .builder()
                .id(contact.getId())
                .name(contact.getName())
                .accountNumber(contact.getAccountNumber())
                .bankId(contact.getBank().getId())
                .bankName(contact.getBank().getName())
                .cost(getTransferCost(contact))
                .build();
    }

    @Override
    public ContactResponse updateContactById(String id, ContactRequest contactRequest) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        Bank bank = bankRepository.findById(contactRequest.getBankId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Bank tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (contactRepository.existsByUserAndNameAndIdNotAndDeleteIsFalseOrUserAndAccountNumberAndIdNotAndDeleteIsFalse(user, contactRequest.getName(), id, user, contactRequest.getAccountNumber(), id)){
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is a contact with the same name / account number");
        }

        if (bank.getPrimary()) {
            if(!(userRepository.existsByAccountNumber(contactRequest.getAccountNumber()))) {
                throw new ApiException(HttpStatus.NOT_FOUND, "There is no beasy account with inputed account number registered");
            }
        }

        contact.setName(contactRequest.getName());
        contact.setAccountNumber(contactRequest.getAccountNumber());
        contact.setBank(bank);

        Contact contactResult = contactRepository.save(contact);

        return ContactResponse
                .builder()
                .id(contactResult.getId())
                .name(contactResult.getName())
                .cost(getTransferCost(contact))
                .accountNumber(contactResult.getAccountNumber())
                .bankId(bank.getId())
                .bankName(bank.getName())
                .build();
    }

    @Override
    public boolean deleteContactById(String id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Contact tidak ditemukan"));

        contact.setDelete(true);
        contactRepository.save(contact);
        return true;
    }

    private Integer getTransferCost(Contact contact) {
        int transferCost = 0;

        if (!contact.getBank().getPrimary()) {
            transferCost = TransactionConstants.DIFFERENT_BANK_FEE;
        }

        UserReward reward = getReward();

        if (Objects.nonNull(reward)) transferCost = 0;

        return transferCost;
    }

    private UserReward getReward() {
        List<UserReward> rewards  = userRewardRepository.findByUserIdAndClaimedTrueAndExpiredAtAfter(userInformation.getUserID(), new Date());

        return rewards.stream()
            .filter(userReward -> userReward.getRewardPlanet().getType().equals(RewardPlanetType.TRANSFER))
            .filter(userReward -> userReward.getTotalUsed() < userReward.getRewardPlanet().getAmount())
            .findFirst()
            .orElse(null);
    }

    private List<Contact> fetchRecentContact(List<Contact> contacts) {
        List<Contact> recentContacts = new ArrayList<>();

        contacts.stream().forEach(contact -> {
            if (recentContacts.size() < 3) {
                if (!CollectionUtils.contains(recentContacts.iterator(), contact) && !contact.getDelete()) {
                    recentContacts.add(contact);
                }
            }
        });

        return recentContacts;
    }

    private List<ContactResponse> toListResponse(List<Contact> contacts) {
        UserReward reward = getReward();

        return contacts
            .stream()
            .map(contact -> ContactResponse
                .builder()
                .id(contact.getId())
                .name(contact.getName())
                .accountNumber(contact.getAccountNumber())
                .bankId(contact.getBank().getId())
                .bankName(contact.getBank().getName())
                .cost(!contact.getBank().getPrimary() ? (Objects.nonNull(reward) ? 0 : TransactionConstants.DIFFERENT_BANK_FEE) : 0)
                .build())
            .collect(Collectors.toList());
    }

    private List<Contact> getFromTransaction(List<Transaction> transactions) {
        return transactions.stream()
            .filter(transaction -> transaction.getTransfer().getContact() != null)
            .map(transaction -> transaction.getTransfer().getContact())
            .collect(Collectors.toList());
    }
}
