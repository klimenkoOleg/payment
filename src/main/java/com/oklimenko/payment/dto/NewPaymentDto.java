package com.oklimenko.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@ToString
public class NewPaymentDto implements Serializable {
    private long clientId;
    private long serviceId;
    private BigDecimal amount;
    private String clientComment;
}
