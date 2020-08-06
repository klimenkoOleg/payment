package com.oklimenko.payment.mapper;

import com.oklimenko.payment.dto.NewPaymentDto;
import com.oklimenko.payment.dto.NewPaymentSystemDto;
import com.oklimenko.payment.dto.SavedPaymentDto;
import com.oklimenko.payment.dto.ToProcessingPaymentDto;
import com.oklimenko.payment.model.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentEntity createPaymentRequestToEntity(NewPaymentDto newPaymentDto);
    NewPaymentSystemDto mapToNewPaymentSystemDto(PaymentEntity paymentEntity);

    ToProcessingPaymentDto map(NewPaymentDto payment);

    void map(@MappingTarget NewPaymentSystemDto newPaymentSystemDto, NewPaymentDto payment);

    NewPaymentSystemDto map(SavedPaymentDto savedPaymentDto);
}
