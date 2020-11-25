package dev.jooz.Web.domain.account;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

public class AccountDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq{
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @Enumerated(EnumType.STRING)
        private AccountRole role;

        @Builder
        public CreateReq(String username, String password,AccountRole role){
            this.username=username;
            this.password=password;
            this.role=role;
        }

        public Account toEntity(){
            return Account.builder()
                    .username(username)
                    .password(password)
                    .role(role)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginReq{
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

        @Builder
        public LoginReq(String username,String password){
            this.username=username;
            this.password=password;
        }
    }

    @Getter
    public static class AccountRes{
        private String username;
        private String accessToken;
        private String refreshToken;

        @Builder
        public AccountRes(String username,String accessToken,String refreshToken){
            this.username=username;
            this.accessToken=accessToken;
            this.refreshToken=refreshToken;
        }
    }
}
