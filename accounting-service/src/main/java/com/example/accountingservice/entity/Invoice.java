package com.example.accountingservice.entity;

import javax.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long paymentId;
    private Long clientId;
    private BigDecimal amount;
    private LocalDateTime issuedAt;
    private String status;
    private String pdfUrl;
}
