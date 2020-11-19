package dev.jooz.Web.domain.image.exception;

import lombok.Getter;

@Getter
public class TooManyImageException extends RuntimeException{

    private String field;

    public TooManyImageException(){
        this.field="TooManyImage";
    }
}
