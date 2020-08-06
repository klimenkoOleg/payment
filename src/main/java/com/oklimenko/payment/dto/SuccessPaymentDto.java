package com.oklimenko.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccessPaymentDto {
    public UUID paymentId;
}
