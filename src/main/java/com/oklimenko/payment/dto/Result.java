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
import static org.springframework.http.HttpStatus.OK;

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
public class Result {

    public static final Integer DUPLICATE_IDEMPOTENCY_KEY = 5001;

    @JsonFormat(shape = STRING, pattern = DATE_WITH_TIME_PATTERN, timezone = TIMEZONE)
    private LocalDateTime timestamp;
    /**
     * Returning HTTP status code
     */
    private Integer status;
    /**
     * Object ID (if present)
     */
    private Long id;
    /**
     * Result description text
     */
    private String message;
    /**
     * Technical details of the errors occurred (data, log, stacktrace etc.)
     */
    private String debugInfo;
    /**
     * Buziness status code
     */
    private Integer code;

    public static Result ok() {
        return new Result()
                .setTimestamp(LocalDateTime.now())
                .setStatus(OK.value());
//                .setId(id);
    }

    public static Result error(String message) {
        return new Result()
                .setTimestamp(LocalDateTime.now())
                .setStatus(INTERNAL_SERVER_ERROR.value())
                .setMessage(message);
//                .setId(id);
    }
}
