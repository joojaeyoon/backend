package dev.jooz.Web.domain.image.exception;

import lombok.Getter;

@Getter
public class NoImageException extends RuntimeException{

    private String ext;
    private String field;

    public NoImageException(String ext){
        this.field="noImg";
        this.ext=ext;
    }
}
