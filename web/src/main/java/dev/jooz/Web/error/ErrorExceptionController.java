package dev.jooz.Web.error;

import dev.jooz.Web.exception.CustomException;
import dev.jooz.Web.exception.account.*;
import dev.jooz.Web.exception.image.NoFileUploadException;
import dev.jooz.Web.exception.image.NoImageException;
import dev.jooz.Web.exception.image.TooManyImageException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNoSuchElementException(NoSuchElementException e) {
        final ErrorCode noSuchElement = ErrorCode.ENTITY_NOT_FOUND;
        return buildError(noSuchElement);
    }

    @ExceptionHandler({
            SignatureException.class,
            MalformedJwtException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse JWTException(RuntimeException e) {
        return buildError(ErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler({
            EmailExistException.class,
            UsernameExistsException.class,
            NoImageException.class,
            NoFileUploadException.class,
            TooManyImageException.class,
            InvalidTokenException.class,
            UserNotExistException.class,
            PasswordNotMatchException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse CustomErrorException(CustomException e) {
        return buildError(e.getErrorCode());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    protected ErrorResponse ExpiredJwtException(ExpiredJwtException e){
        return buildError(ErrorCode.EXPIRED_JWT);
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse buildErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }
}
