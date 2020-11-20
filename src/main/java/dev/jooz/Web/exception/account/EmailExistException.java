package dev.jooz.Web.exception.account;

import lombok.Getter;

@Getter
public class EmailExistException extends RuntimeException{

    private String email;
    private String field;

    public EmailExistException(String email){
        this.field="email";
        this.email=email;
    }
}
