package dev.jooz.Web.exception;

import dev.jooz.Web.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    protected ErrorCode errorCode;
    protected String field;

}
