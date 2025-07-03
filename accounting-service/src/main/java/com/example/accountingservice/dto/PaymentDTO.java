
package com.example.accountingservice.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Long userId;
    private int price;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}