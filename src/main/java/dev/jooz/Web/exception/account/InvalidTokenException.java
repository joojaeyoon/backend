package dev.jooz.Web.exception.account;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException{
    private String token;
    private String field;

    public InvalidTokenException(String token){
        this.token=token;
        this.field="token";

    }
}
