package com.synrgybootcamp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creditcardbill")
public class CreditCardBill {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Column(name = "bank")
    private String bank;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "bill_payment")
    private Integer billPament;

    @Column(name = "minimum_bill")
    private Integer minimumBill;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", updatable = false)
    private Date date = new Date();

    @Column(name = "is_paid_off_bill")
    private Boolean paidOffBill;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
