package com.oklimenko.payment.exception;

/**
 * Top level parent exception class for all business related exceptions of service pay application.
 */
public class PaymentServiceException extends RuntimeException {

  public PaymentServiceException(String message) {
    super(message);
  }

  public PaymentServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
