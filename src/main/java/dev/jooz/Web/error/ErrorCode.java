package dev.jooz.Web.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCOUNT_NOT_FOUND("AC_001","해당 회원을 찾을 수 없습니다.",404),
    EMAIL_ALREADY_EXIST("AC_002","이메일이 이미 사용중입니다.",400),
    USERNAME_ALREADY_EXIST("AC_003","유저 이름이 이미 사용중입니다.",400),
    INPUT_VALUE_INVALID("INP_001","입력값이 올바르지 않습니다.",400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code,String message,int status){
        this.code=code;
        this.message=message;
        this.status=status;
    }

}
