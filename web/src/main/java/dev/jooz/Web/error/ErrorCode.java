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
    TOO_MANY_IMAGE("IMG_003","이미지는 5장까지 등록가능합니다.",400),
    INVALID_TOKEN("ACC_001","유효하지 않은 토큰입니다.",400),
    USER_NOT_EXIST("ACC_002","존재하지 않는 유저입니다.",400),
    PASSWORD_NOT_MATCH("ACC_003","패스워드가 일치하지 않습니다.",400),
    EXPIRED_JWT("JWT_001","토큰이 만료되었습니다.",400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code,String message,int status){
        this.code=code;
        this.message=message;
        this.status=status;
    }

}
