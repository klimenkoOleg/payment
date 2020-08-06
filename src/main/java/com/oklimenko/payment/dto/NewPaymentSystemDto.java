package com.oklimenko.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class NewPaymentSystemDto {
    private UUID paymentId;
    private long clientId;
    private long serviceId;
    private BigDecimal amount;
    private String clientComment;
    private LocalDateTime dateCreated;
//    private LocalDateTime dateCompleted;
}
