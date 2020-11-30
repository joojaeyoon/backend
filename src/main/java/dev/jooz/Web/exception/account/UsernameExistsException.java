package dev.jooz.Web.exception.account;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class UsernameExistsException extends CustomException {
    private String username;

    public UsernameExistsException(String username){
        this.username=username;
        this.field="username";
        this.errorCode=ErrorCode.USERNAME_ALREADY_EXIST;
    }
}
