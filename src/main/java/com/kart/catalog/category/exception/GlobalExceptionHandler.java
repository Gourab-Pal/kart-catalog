package com.kart.catalog.category.exception;

import com.kart.catalog.category.dto.CategoryNotFoundResponse;
import com.kart.catalog.category.dto.DataIntegrityViolationExceptionResponse;
import com.kart.catalog.category.dto.MethodArgumentTypeMismatchExceptionResponse;
import com.kart.catalog.category.dto.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CategoryNotFoundResponse handleCategoryNotFoundException(CategoryNotFoundException exception) {
        log.error("Resource not found--> {}", exception.getMessage());
        return CategoryNotFoundResponse.getCategoryNotFoundResponse(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        Map<String,String> errors = new LinkedHashMap<>();
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("Invalid keys parsed while creating request--> {}", errors);
        return new ValidationErrorResponse(
                "Request validations failed",
                errors,
                OffsetDateTime.now()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public DataIntegrityViolationExceptionResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("Database integrity violation--> {}", exception.getMessage());
        return new DataIntegrityViolationExceptionResponse(
                "Conflict while adding data to database",
                OffsetDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MethodArgumentTypeMismatchExceptionResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error("Invalid argument parsed in url--> {}", exception.getMessage());
        return new MethodArgumentTypeMismatchExceptionResponse(
                "Invalid argument parsed in url",
                OffsetDateTime.now()
        );
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public DataIntegrityViolationExceptionResponse handleCategoryNameAlreadyExistsException(CategoryNameAlreadyExistsException exception) {
        log.error("Updated category name can not be same as current name --> {}", exception.getUpdatedName());
        return new DataIntegrityViolationExceptionResponse(
               "Updated category name can not be same as current name",
               OffsetDateTime.now()
        );
    }
}
