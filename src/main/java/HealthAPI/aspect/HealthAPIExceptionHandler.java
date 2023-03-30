package HealthAPI.aspect;

import HealthAPI.exception.*;
import HealthAPI.messages.Responses;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ControllerAdvice
public class HealthAPIExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(HealthAPIExceptionHandler.class);

    @ExceptionHandler(value = {TimeSlotNotFound.class})
    public ResponseEntity<String> handleTimeSlotNotFound(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {UserNotFound.class})
    public ResponseEntity<String> handleUserNotFound(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {NotOldEnough.class})
    public ResponseEntity<String> handleNotOldEnough(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {TooOld.class})
    public ResponseEntity<String> handleTooOld(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {InvalidEmail.class})
    public ResponseEntity<String> handleInvalidEmail(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {ClientNotFound.class})
    public ResponseEntity<String> handleClientNotFound(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {AppointmentNotFound.class})
    public ResponseEntity<String> handleAppointmentNotFound(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {AppointmentAlreadyActive.class})
    public ResponseEntity<String> handleAppointmentAlreadyActive(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {AppointmentAlreadyInactive.class})
    public ResponseEntity<String> handleAppointmentAlreadyInactive(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {InvalidName.class})
    public ResponseEntity<String> handleInvalidName(Exception ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = Responses.INVALID_REQUEST_FORMAT;
        Throwable cause = ex.getRootCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException formatException = (InvalidFormatException) cause;
            errorMessage = String.format("%s is an invalid %s", formatException.getValue(), formatException.getTargetType().getSimpleName());
        } else if (cause instanceof DateTimeParseException) {
            DateTimeParseException dateTimeParseException = (DateTimeParseException) cause;
            errorMessage = String.format("Failed to deserialize %s: %s", dateTimeParseException.getParsedString(), dateTimeParseException.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = Responses.DATA_INTEGRITY_VIOLATION;
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
            String constraintName = constraintViolationException.getLocalizedMessage();
            if (constraintName != null && constraintName.startsWith("UK_")) {
                String duplicateValue = extractDuplicateValueFromConstraintName(constraintName);
                if (duplicateValue != null) {
                    errorMessage = "Duplicate entry for value '" + duplicateValue + "'";
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    private String extractDuplicateValueFromConstraintName(String errorMessage) {
        Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPhoneNumber.class)
    public ResponseEntity<String> handleInvalidPhoneNumber(InvalidPhoneNumber ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidNIF.class)
    public ResponseEntity<String> handleInvalidNIF(InvalidNIF ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Responses.INVALID_USER);
    }

    @ExceptionHandler(UserAlreadyActive.class)
    public ResponseEntity<String> handleUserAlreadyActive(UserAlreadyActive ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAction.class)
    public ResponseEntity<String> handleUnauthorizedAction(UnauthorizedAction ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Responses.INVALID_INPUT);
    }

    @ExceptionHandler(StartHourAfterFinishingHour.class)
    public ResponseEntity<String> handleStartHourAfterFinishingHour(StartHourAfterFinishingHour ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTimeSlotDate.class)
    public ResponseEntity<String> handleInvalidTimeSlotDate(InvalidTimeSlotDate ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TimeSlotsAlreadyExists.class)
    public ResponseEntity<String> handleTimeSlotsAlreadyExists(TimeSlotsAlreadyExists ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TimeSlotAlreadyBooked.class)
    public ResponseEntity<String> handleTimeSlotAlreadyBooked(TimeSlotAlreadyBooked ex) {
        logger.error("Known Exception: " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Unknown Exception: " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }

}