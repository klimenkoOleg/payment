package com.oklimenko.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oklimenko.payment.dto.ErrorResult;
import com.oklimenko.payment.dto.NewPaymentDto;
import com.oklimenko.payment.dto.NewPaymentSystemDto;
import com.oklimenko.payment.dto.SavedPaymentDto;
import com.oklimenko.payment.dto.SuccessPaymentDto;
import com.oklimenko.payment.dto.ToProcessingPaymentDto;
import com.oklimenko.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${external.paymentGateUri}")
    private String paymentGateUri;

    @Value("${kafka.topic}")
    private String kafkaTopic;

        private final KafkaTemplate<Long, NewPaymentSystemDto> kafkaTemplate;

    private final PaymentMapper paymentMapper;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Override
    public SuccessPaymentDto processPayment(UUID idempotancyKey, NewPaymentDto payment) {
        ToProcessingPaymentDto toProcessingPaymentDto = paymentMapper.map(payment);
        toProcessingPaymentDto.setIdempotancyKey(idempotancyKey);

        SavedPaymentDto savedPaymentDto = sendToProcessing(toProcessingPaymentDto);

        NewPaymentSystemDto newPaymentSystemDto = paymentMapper.map(savedPaymentDto);
        paymentMapper.map(newPaymentSystemDto, payment);
//        NewPaymentSystemDto paymentSystemDto = payment(payment);
//        Long paymentId = 10L;
        sendToPaymentGateway(newPaymentSystemDto);

        return new SuccessPaymentDto(newPaymentSystemDto.getPaymentId());
    }

    /*@SneakyThrows
    public SuccessPaymentDto performPayment(UUID idempotancyKey, NewPaymentDto payment) {

        Optional<PaymentEntity> paymentEntityToCheckIdempotency =
                paymentRepository.findByIdempotancyKey(idempotancyKey);

        if (paymentEntityToCheckIdempotency.isPresent()) {
            throw new DuplicatedIdempotenceKeyValidationException(idempotancyKey);
        }

        PaymentEntity paymentEntity = paymentMapper.createPaymentRequestToEntity(payment);
        paymentEntity.setStatus(PAYMENT_CREATED);
        paymentEntity.setIdempotancyKey(idempotancyKey);
        paymentEntity.setDateCreated(LocalDateTime.now());
        // this timeout is dirty emulation of external payment system processing
        Thread.sleep(500 + random.nextInt(1000));
        paymentEntity.setDateCompleted(LocalDateTime.now());

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);

        return new SuccessPaymentDto(savedPaymentEntity.getId());
    }*/

    @SneakyThrows
    public SavedPaymentDto sendToProcessing(ToProcessingPaymentDto newPaymentDto) {

        HttpEntity<ToProcessingPaymentDto> request = new HttpEntity<>(newPaymentDto);

        ResponseEntity<SavedPaymentDto> response =
                restTemplate.postForEntity(new URI(paymentGateUri), request, SavedPaymentDto.class);

        // TODO process DuplicatedIdempotenceKeyValidationException
//        response.getStatusCode()
//        if (DUPLICATE_IDEMPOTENCY_KEY.equals(response.getBody().getCode())) {
//            throw new DuplicatedIdempotenceKeyValidationException(response.getBody().getMessage());
//        }
        return response.getBody();
    }

    private void sendToPaymentGateway(NewPaymentSystemDto dto) {
        kafkaTemplate.send(kafkaTopic, dto);
    }
}