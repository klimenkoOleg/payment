package com.oklimenko.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//@Entity
//@Table(name = "payment")
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)
//@Data
public class PaymentEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long clientId;
    private String status;
    private long serviceId;
    private String clientComment;
    private BigDecimal amount;
//    @Column(nullable = false, columnDefinition = "uuid")
    private UUID idempotancyKey;
    private LocalDateTime dateCreated;
    private LocalDateTime dateCompleted;
}
