package com.example.paymentservice.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "user_id", unique=true, nullable=false)
    private Long userId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name= "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name= "expiry_date", nullable = false)
    private String expiryDate;

    @Column(name = "cvv", nullable = false)
    private String cvv;



}
