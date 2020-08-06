package com.oklimenko.payment.controller;

import com.oklimenko.payment.dto.NewPaymentDto;
import com.oklimenko.payment.dto.SuccessPaymentDto;
import com.oklimenko.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping("/payment/create")
    public SuccessPaymentDto createPayment(@RequestHeader UUID idempotanceKey, @RequestBody NewPaymentDto payment) {
        return paymentService.processPayment(idempotanceKey, payment);
    }
}
