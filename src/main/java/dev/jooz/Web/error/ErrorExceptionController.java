package dev.jooz.Web.error;

import dev.jooz.Web.exception.account.EmailExistException;
import dev.jooz.Web.exception.account.UsernameExistsException;
import dev.jooz.Web.exception.image.NoFileUploadException;
import dev.jooz.Web.exception.image.NoImageException;
import dev.jooz.Web.exception.image.TooManyImageException;
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
        return buildErrors(ErrorCode.INPUT_VALUE_INVALID,fieldErrors);
    }

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleEmailExistException(EmailExistException e){
        final ErrorCode emailExist=ErrorCode.EMAIL_ALREADY_EXIST;
        return buildError(emailExist);
    }

    @ExceptionHandler(UsernameExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleUsernameExistException(UsernameExistsException e){
        final ErrorCode usernameExist=ErrorCode.USERNAME_ALREADY_EXIST;
        return buildError(usernameExist);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNoSuchElementException(NoSuchElementException e){
        final ErrorCode noSuchElement=ErrorCode.ENTITY_NOT_FOUND;
        return buildError(noSuchElement);
    }

    @ExceptionHandler(NoImageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleNoImageException(NoImageException e){
        final ErrorCode noImage=ErrorCode.NO_IMAGE;
        return buildError(noImage);
    }

    @ExceptionHandler(NoFileUploadException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleNoFileUploadException(NoFileUploadException e){
        final ErrorCode noFile=ErrorCode.NO_FILE_UPLOAD;
        return buildError(noFile);
    }

    @ExceptionHandler(TooManyImageException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleTooManyImageException(TooManyImageException e){
        final ErrorCode tooMany=ErrorCode.TOO_MANY_IMAGE;
        return buildError(tooMany);
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
