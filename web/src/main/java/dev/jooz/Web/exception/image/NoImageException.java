package dev.jooz.Web.exception.image;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class NoImageException extends CustomException {

    private String ext;

    public NoImageException(String ext){
        this.field="noImg";
        this.ext=ext;
        this.errorCode=ErrorCode.NO_IMAGE;
    }
}
