package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.PaymentMerchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMerchantRepository extends JpaRepository<PaymentMerchant, String> {
}
