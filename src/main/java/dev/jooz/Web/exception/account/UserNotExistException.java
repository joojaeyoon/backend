package dev.jooz.Web.exception.account;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class UserNotExistException extends CustomException {
    private String username;

    public UserNotExistException(String username){
        this.username=username;
        this.field="username";
        this.errorCode=ErrorCode.USER_NOT_EXIST;
    }
}
