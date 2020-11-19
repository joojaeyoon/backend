package dev.jooz.Web.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ENTITY_NOT_FOUND("ERR_001","해당 값을 찾을 수 없습니다.",404),
    EMAIL_ALREADY_EXIST("ERR_002","이메일이 이미 사용중입니다.",400),
    USERNAME_ALREADY_EXIST("ERR_003","유저 이름이 이미 사용중입니다.",400),
    INPUT_VALUE_INVALID("ERR_004","입력값이 올바르지 않습니다.",400),
    NO_IMAGE("IMG_001","이미지 파일만 등록 가능합니다.",400),
    NO_FILE_UPLOAD("IMG_002","업로드할 파일이 없습니다.",400),
    TOO_MANY_IMAGE("IMG_003","이미지는 5장까지 등록가능합니다.",400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code,String message,int status){
        this.code=code;
        this.message=message;
        this.status=status;
    }

}
