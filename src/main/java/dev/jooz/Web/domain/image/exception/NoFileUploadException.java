package dev.jooz.Web.domain.image.exception;

import lombok.Getter;

@Getter
public class NoFileUploadException extends RuntimeException{
    private String field;

    public NoFileUploadException(){
        this.field="NoFileUpload";
    }
}
