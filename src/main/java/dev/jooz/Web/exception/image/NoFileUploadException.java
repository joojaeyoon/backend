package dev.jooz.Web.exception.image;

import lombok.Getter;

@Getter
public class NoFileUploadException extends RuntimeException{
    private String field;

    public NoFileUploadException(){
        this.field="NoFileUpload";
    }
}
