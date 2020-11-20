package dev.jooz.Web.exception.account;

import lombok.Getter;

@Getter
public class UsernameExistsException extends RuntimeException{
    String username;
    String field;

    public UsernameExistsException(String username){
        this.username=username;
        this.field="username";
    }
}
