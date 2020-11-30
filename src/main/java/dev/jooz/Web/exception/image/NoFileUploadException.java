package dev.jooz.Web.exception.image;

import dev.jooz.Web.error.ErrorCode;
import dev.jooz.Web.exception.CustomException;
import lombok.Getter;

@Getter
public class NoFileUploadException extends CustomException {
    public NoFileUploadException(){
        this.field="NoFileUpload";
        this.errorCode=ErrorCode.NO_FILE_UPLOAD;
    }
}
