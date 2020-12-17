package dev.jooz.Web.exception.account;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class InvalidTokenException extends CustomException {
    private String token;

    public InvalidTokenException(String token){
        this.token=token;
        this.field="token";
        this.errorCode=ErrorCode.INVALID_TOKEN;
    }
}
