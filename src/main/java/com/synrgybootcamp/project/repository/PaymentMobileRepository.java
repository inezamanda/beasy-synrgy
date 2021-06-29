package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.PaymentMobile;
import com.synrgybootcamp.project.enums.MobileOperator;
import com.synrgybootcamp.project.enums.MobileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMobileRepository extends JpaRepository<PaymentMobile, String> {
    List<PaymentMobile> findByMobileOperatorAndMobileTypeOrderByPriceAsc(MobileOperator mobileOperator, MobileType mobileType);
}
