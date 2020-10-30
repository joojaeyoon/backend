package dev.jooz.Web.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ENTITY_NOT_FOUND("ERR_001","해당 값을 찾을 수 없습니다.",404),
    EMAIL_ALREADY_EXIST("ERR_002","이메일이 이미 사용중입니다.",400),
    USERNAME_ALREADY_EXIST("ERR_003","유저 이름이 이미 사용중입니다.",400),
    INPUT_VALUE_INVALID("ERR_004","입력값이 올바르지 않습니다.",400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code,String message,int status){
        this.code=code;
        this.message=message;
        this.status=status;
    }

}
