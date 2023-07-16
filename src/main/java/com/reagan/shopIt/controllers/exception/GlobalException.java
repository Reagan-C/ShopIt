package com.reagan.shopIt.controllers.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.reagan.shopIt.model.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, Object> validation(MethodArgumentNotValidException exception) {
                Map<String, Object> errors = new HashMap<String, Object>();
                Map<String, Object> errorMap = new HashMap<String, Object>();
                exception.getBindingResult().getAllErrors().forEach(error -> {
                        if (error instanceof  FieldError) {
                                String fieldName = ((FieldError) error).getField();
                                String errorMessage = error.getDefaultMessage();
                                errorMap.put(fieldName, errorMessage);
                        }else if(error != null){
                                String objectName = ((ObjectError) error).getObjectName();
                                String errorMessage = error.getDefaultMessage();
                                errorMap.put(objectName, errorMessage);
                        }
                });
                errors.put("message", errorMap);
                errors.put("error-type", "Data Validation");
                return errors;
        }


        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CountryNotFoundException.class)
        public Object notFound(CountryNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", CountryNotFoundException.ENTITY_NAME);
                errors.put("message", ex.getMessage());
                ex.setCode(HttpStatus.NOT_FOUND.value());
                errors.put("code", ex.getCode().toString());
                return errors;
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CountryIDNotFoundException.class)
        public Object notFound(CountryIDNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Country ID");
                errors.put("message", "Country ID not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(CountryDuplicateEntityException.class)
        public Object duplicate(CountryDuplicateEntityException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", CountryDuplicateEntityException.ENTITY_NAME);
                errors.put("message", ex.getMessage());
                ex.setCode(HttpStatus.CONFLICT.value());
                errors.put("code", ex.getCode().toString());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(UserRoleNotFoundException.class)
        public Object notFound(UserRoleNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", UserRoleNotFoundException.ENTITY_NAME);
                errors.put("message", ex.getMessage());
                ex.setCode(HttpStatus.NOT_FOUND.value());
                errors.put("code", ex.getCode().toString());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(UserRoleDuplicateEntityException.class)
        public Object duplicate(UserRoleDuplicateEntityException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", UserRoleDuplicateEntityException.ENTITY_NAME);
                errors.put("message", ex.getMessage());
                ex.setCode(HttpStatus.CONFLICT.value());
                errors.put("code", ex.getCode().toString());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(value = {AccessDeniedException.class})
        public Object forbidden(AccessDeniedException ex, HttpServletRequest request) {
                final Map<String, Object> body = new HashMap<>();
                body.put("error", "Forbidden");
                body.put("path", request.getServletPath());
                body.put("message", "You are not allowed to access this resource");
                body.put("code", String.valueOf(HttpStatus.FORBIDDEN.value()));
                return body;
        }

        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(AuthenticationException.class)
        public Object unauthorized(AuthenticationException ex, HttpServletRequest request) {
                final Map<String, Object> body = new HashMap<>();
                body.put("error", "Unauthorized");
                body.put("message", ex.getMessage());
                body.put("code", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                return body;
        }

        @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public Object support(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
                final Map<String, Object> body = new HashMap<>();
                body.put("error", "Method Not Supported");
                body.put("path", request.getServletPath());
                body.put("message", "Request method " + request.getMethod() + " not supported");
                body.put("code", String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
                return body;
        }

        @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public Object support(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
                final Map<String, Object> body = new HashMap<>();
                body.put("error", "Content type not Supported");
                body.put("path", request.getServletPath());
                body.put("message", "Content type " + request.getContentType() + " not supported");
                body.put("code", String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
                return body;
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(InvalidOtpException.class)
        public Object otp(InvalidOtpException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", "Otp");
                errors.put("message", ex.getMessage());
                errors.put("code", HttpStatus.BAD_REQUEST.value());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
        @ExceptionHandler(ConfirmedOtpException.class)
        public Object confirmedOtp(ConfirmedOtpException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("message", ex.getMessage());
                errors.put("code", HttpStatus.NOT_ACCEPTABLE.value());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
        @ExceptionHandler(ConfirmedPasswordChangeOtpException.class)
        public Object passwordOtp(ConfirmedPasswordChangeOtpException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("message", ex.getMessage());
                errors.put("code", HttpStatus.NOT_ACCEPTABLE.value());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
        @ExceptionHandler(ExpiredOtpException.class)
        public Object otp(ExpiredOtpException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("message", ex.getMessage());
                errors.put("code", HttpStatus.NOT_ACCEPTABLE.value());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(InsufficientDaysBeforeUpdateException.class)
        public Object invalidTime(InsufficientDaysBeforeUpdateException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("message", ex.getMessage());
                errors.put("code", HttpStatus.FORBIDDEN.value());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(DataIntegrityViolationException.class)
        public Object unique(DataIntegrityViolationException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Unknown");
                errors.put("message", "The referenced entity id does not exist, all existent and new entities field" +
                        " must be unique and referenced ids must exist.");
                errors.put("code" , Integer.toString(HttpStatus.BAD_REQUEST.value()));
                return errors;
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(IllegalArgumentException.class)
        public Object wrong(IllegalArgumentException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Unknown");
                errors.put("message", "The argument passed is not expected");
                errors.put("code" , Integer.toString(HttpStatus.BAD_REQUEST.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(IncorrectAuthenticationException.class)
        public Object wrong(IncorrectAuthenticationException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Authentication");
                errors.put("message", "Username or password incorrect");
                errors.put("code" , Integer.toString(HttpStatus.BAD_REQUEST.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(UserAlreadyExistsException.class)
        public Object exists(UserAlreadyExistsException ex) {
                final Map<String, Object> errors = new HashMap<String, Object>();
                errors.put("entityName", UserAlreadyExistsException.ENTITY_NAME);
                errors.put("message", ex.getMessage());
                ex.setCode(HttpStatus.CONFLICT.value());
                errors.put("code", ex.getCode().toString());
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(UserNameNotFoundException.class)
        public Object notFound(UserNameNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "UserName");
                errors.put("message", "Username not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(UserIDNotFoundException.class)
        public Object notFound(UserIDNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "UserID");
                errors.put("message", "UserID not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
        @ExceptionHandler(InsufficientItemQuantityException.class)
        public Object notFound(InsufficientItemQuantityException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Item");
                errors.put("message", "Item quantity insufficient");
                errors.put("code" , Integer.toString(HttpStatus.EXPECTATION_FAILED.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(ItemNotFoundException.class)
        public Object notFound(ItemNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Item");
                errors.put("message", "Item not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CartEmptyException.class)
        public Object cartEmpty(CartEmptyException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "cart");
                errors.put("message", "cart empty");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(PendingOrderNotFoundException.class)
        public Object notFound(PendingOrderNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "pending_order");
                errors.put("message", "No pending order found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(TokenNotFoundException.class)
        public Object tokenNotFound(TokenNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Token");
                errors.put("message", "Token empty");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CategoryIDNotFoundException.class)
        public Object notFound(CategoryIDNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Category");
                errors.put("message", "Category not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CategoryNameNotFoundException.class)
        public Object notFound(CategoryNameNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Category");
                errors.put("message", "Category not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(CategoryExistsException.class)
        public Object exists(CategoryExistsException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Category");
                errors.put("message", "Category already exists");
                errors.put("code" , Integer.toString(HttpStatus.CONFLICT.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(AdminAlreadyExistsException.class)
        public Object exists(AdminAlreadyExistsException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Admin");
                errors.put("message", "Admin already exists");
                errors.put("code" , Integer.toString(HttpStatus.CONFLICT.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(AdminNotFoundException.class)
        public Object notFound(AdminNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Admin");
                errors.put("message", "User not an admin");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }


        @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
        @ExceptionHandler(EmailDeliveryException.class)
        public Object expectationFailed(EmailDeliveryException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Email");
                errors.put("message", "An error occurred and message could not be delivered");
                errors.put("code" , Integer.toString(HttpStatus.EXPECTATION_FAILED.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.CONFLICT)
        @ExceptionHandler(EmailExistsException.class)
        public Object exists(EmailExistsException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Email");
                errors.put("message", "Email already exists in our record");
                errors.put("code" , Integer.toString(HttpStatus.CONFLICT.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(EmailNotFoundException.class)
        public Object notFound(EmailNotFoundException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Email");
                errors.put("message", "Email not found");
                errors.put("code" , Integer.toString(HttpStatus.NOT_FOUND.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        @ExceptionHandler(DateInvalidException.class)
        public Object invalidDate(DateInvalidException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Date");
                errors.put("message", "Invalid date format");
                errors.put("code" , Integer.toString(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(JwtAuthenticationException.class)
        public Object invalid(JwtAuthenticationException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Jwt authentication");
                errors.put("message", "Failure while generating jwt");
                errors.put("code" , Integer.toString(HttpStatus.UNAUTHORIZED.value()));
                return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(SetAuthenticationFailureException.class)
        public Object authentication(SetAuthenticationFailureException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Authentication");
                errors.put("message", "Can not set authentication");
                errors.put("code" , Integer.toString(HttpStatus.UNAUTHORIZED.value()));
                return errors;
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(JsonParseException.class)
        public Object authentication(JsonParseException ex) {
                final Map<String, Object> errors = new HashMap<>();
                errors.put("entityName", "Json Parser");
                errors.put("message", "could not parse input json");
                errors.put("code" , Integer.toString(HttpStatus.BAD_REQUEST.value()));
                return errors;
        }

}
