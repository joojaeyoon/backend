package dev.jooz.Web.exception.account;

import lombok.Getter;

@Getter
public class UserNotExistException extends RuntimeException{
    private String username;
    private String field;

    public UserNotExistException(String username){
        this.username=username;
        this.field="username";
    }
}
