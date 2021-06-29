package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.CreditCardBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardBillRepository extends JpaRepository<CreditCardBill, String> {
    boolean existsByCreditCardNumber(String creditCardNumber);
    CreditCardBill findByCreditCardNumber(String creditCardNumber);
}
