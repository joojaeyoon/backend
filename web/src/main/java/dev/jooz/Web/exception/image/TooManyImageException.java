package dev.jooz.Web.exception.image;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class TooManyImageException extends CustomException {
    public TooManyImageException(){
        this.field="TooManyImage";
        this.errorCode=ErrorCode.TOO_MANY_IMAGE;
    }
}
