package com.oklimenko.payment.exception;

public class DuplicatedIdempotenceKeyValidationException extends PaymentServiceException {

  public DuplicatedIdempotenceKeyValidationException(String message) {
    super(message);
  }
}