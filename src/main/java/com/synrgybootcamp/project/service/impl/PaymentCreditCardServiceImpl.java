package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.CreditCardBill;
import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.CreditCardBillRepository;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.TransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PaymentCreditCardService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.CreditCardBillUtil;
import com.synrgybootcamp.project.web.model.request.CreditCardPaymentRequest;
import com.synrgybootcamp.project.web.model.request.CreditCardRequest;
import com.synrgybootcamp.project.web.model.response.CreditCardPaymentResponse;
import com.synrgybootcamp.project.web.model.response.CreditCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class PaymentCreditCardServiceImpl implements PaymentCreditCardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditCardBillRepository creditCardBillRepository;

    @Autowired
    private CreditCardBillUtil creditCardBillUtil;

    @Override
    public CreditCardResponse getCreditCardBill(CreditCardRequest creditCardRequest) {
        if (!(creditCardBillRepository.existsByCreditCardNumber(creditCardRequest.getCreditcardnumber()))) {
            CreditCardBill creditCardBill = addBill(creditCardRequest);
            return CreditCardResponse
                    .builder()
                    .name(creditCardBill.getUser().getFullName())
                    .creditcardnumber(creditCardBill.getCreditCardNumber())
                    .billpayment(creditCardBill.getBillPament())
                    .minimumpayment(creditCardBill.getMinimumBill())
                    .bank(creditCardBill.getBank())
                    .on(creditCardBill.getDate())
                    .build();
        } else {
            CreditCardBill creditCardBill = creditCardBillRepository.findByCreditCardNumber(creditCardRequest.getCreditcardnumber());
            if(creditCardBill.getPaidOffBill() == true) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Tagihan Lunas");
            }
            return CreditCardResponse
                    .builder()
                    .name(creditCardBill.getUser().getFullName())
                    .creditcardnumber(creditCardBill.getCreditCardNumber())
                    .billpayment(creditCardBill.getBillPament())
                    .minimumpayment(creditCardBill.getMinimumBill())
                    .bank(creditCardBill.getBank())
                    .on(creditCardBill.getDate())
                    .build();
        }
    }

    @Override
    public CreditCardPaymentResponse payCreditCard(CreditCardPaymentRequest creditCardPaymentRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        Pocket pocket = pocketRepository.findPrimaryPocket(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Primary Pocket Not Found"));

        CreditCardBill creditCardBill = creditCardBillRepository.findByCreditCardNumber(creditCardPaymentRequest.getCreditCardNumber());

        if(!(creditCardPaymentRequest.getPin().equals(user.getPin())))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Wrong pin");

        if(creditCardBillRepository.existsByCreditCardNumber(creditCardPaymentRequest.getCreditCardNumber())) {
            int finalBalance = pocket.getBalance() - creditCardPaymentRequest.getAmount();
            if (finalBalance < 0)
                throw new ApiException(HttpStatus.BAD_REQUEST, "Jumlah melebihi saldo");

            pocket.setBalance(finalBalance);
            pocketRepository.save(pocket);

            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
            Date date = new Date();
            Transaction transaction = transactionRepository.save(
                    Transaction
                            .builder()
                            .user(user)
                            .description(creditCardBill.getBank() + " " + creditCardBill.getCardType())
                            .type(TransactionType.PAYMENT_CREDIT_CARD)
                            .totalAmount(creditCardPaymentRequest.getAmount())
                            .date(date)
                            .build()
            );

            Date nextBillDate = creditCardBill.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextBillDate);
            calendar.add(Calendar.MONTH, 1);
            nextBillDate = calendar.getTime();

            int afterSubtraction = creditCardBill.getBillPament() - creditCardPaymentRequest.getAmount();
            if(afterSubtraction != 0) {
                creditCardBill.setDate(nextBillDate);
                creditCardBill.setBillPament(afterSubtraction);
                creditCardBill.setMinimumBill(afterSubtraction * 10 / 100);
                creditCardBill.setPaidOffBill(false);
                creditCardBillRepository.save(creditCardBill);
            } else {
                creditCardBill.setDate(date);
                creditCardBill.setBillPament(afterSubtraction);
                creditCardBill.setBillPament(0);
                creditCardBill.setPaidOffBill(true);
                creditCardBillRepository.save(creditCardBill);
            }
            return CreditCardPaymentResponse
                    .builder()
                    .name(creditCardBill.getUser().getFullName())
                    .amount(creditCardPaymentRequest.getAmount())
                    .creditcardnumber(creditCardPaymentRequest.getCreditCardNumber())
                    .on(transaction.getDate())
                    .build();
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, "Credit card bill not found");
        }
    }

    private CreditCardBill addBill(CreditCardRequest creditCardRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        Integer getBill = creditCardBillUtil.getRandomBill();
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        date = calendar.getTime();
        return creditCardBillRepository.save(
                CreditCardBill
                        .builder()
                        .creditCardNumber(creditCardRequest.getCreditcardnumber())
                        .bank(creditCardBillUtil.getRandomBank())
                        .cardType(creditCardBillUtil.getRandomCardType())
                        .billPament(getBill)
                        .minimumBill(getBill * 10 / 100)
                        .date(date)
                        .user(user)
                        .build()
        );
    }
}
