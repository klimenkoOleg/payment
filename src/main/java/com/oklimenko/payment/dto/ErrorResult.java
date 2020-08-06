package com.oklimenko.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.oklimenko.payment.util.DateFormat.DATE_WITH_TIME_PATTERN;
import static com.oklimenko.payment.util.DateFormat.TIMEZONE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Successful result details
 */
@JsonInclude(NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ErrorResult {

    public static final int BUSINESS_STATUS_DUPLICATE_PAYMENT_ERROR = 5005;

    @JsonFormat(shape = STRING, pattern = DATE_WITH_TIME_PATTERN, timezone = TIMEZONE)
    private LocalDateTime timestamp;
    /**
     * Returning HTTP status code
     */
    private Integer status;
    /**
     * Object ID (if present)
     */
    private UUID id;
    /**
     * Result description text
     */
    private String message;
    /**
     * Technical details of the errors occurred (data, log, stacktrace etc.)
     */
    private String debugInfo;

    public static ErrorResult error(String message) {
        return new ErrorResult()
                .setTimestamp(LocalDateTime.now())
                .setStatus(INTERNAL_SERVER_ERROR.value())
                .setMessage(message);
    }

    public static ErrorResult errorDuplicatePayment(UUID idempotencyKey) {
        return new ErrorResult()
                .setTimestamp(LocalDateTime.now())
                .setStatus(BUSINESS_STATUS_DUPLICATE_PAYMENT_ERROR)
                .setMessage("Duplicate payment idempotency key")
                .setId(idempotencyKey);
    }
}