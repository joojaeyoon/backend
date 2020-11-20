package dev.jooz.Web.exception.image;

import lombok.Getter;

@Getter
public class TooManyImageException extends RuntimeException{

    private String field;

    public TooManyImageException(){
        this.field="TooManyImage";
    }
}
