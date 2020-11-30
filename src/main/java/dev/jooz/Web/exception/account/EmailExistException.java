package dev.jooz.Web.exception.account;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class EmailExistException extends CustomException {
    private String email;

    public EmailExistException(String email){
        this.field="email";
        this.email=email;
        this.errorCode=ErrorCode.EMAIL_ALREADY_EXIST;
    }
}
