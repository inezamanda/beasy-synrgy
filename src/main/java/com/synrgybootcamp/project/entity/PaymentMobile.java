package com.synrgybootcamp.project.entity;

import com.synrgybootcamp.project.enums.MobileOperator;
import com.synrgybootcamp.project.enums.MobileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paymentmobile")
public class PaymentMobile {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MobileType mobileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private MobileOperator mobileOperator;
}
