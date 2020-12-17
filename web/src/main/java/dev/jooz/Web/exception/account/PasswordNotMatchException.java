package dev.jooz.Web.exception.account;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class PasswordNotMatchException extends CustomException {
    public PasswordNotMatchException(){
        this.field="password";
        this.errorCode= ErrorCode.PASSWORD_NOT_MATCH;
    }
}
