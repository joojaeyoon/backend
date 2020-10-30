package dev.jooz.Web.domain.account;

import dev.jooz.Web.util.PasswordEncoding;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq{
        @Email
        @NotEmpty
        private String email;
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

        @Builder
        public CreateReq(String email, String username, String password){
            this.email=email;
            this.username=username;
            this.password=password;
        }

        public Account toEntity(){
            PasswordEncoding passwordEncoding=new PasswordEncoding();
            this.password=passwordEncoding.encode(this.password);
            return Account.builder()
                    .email(email)
                    .username(username)
                    .password(password)
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
        private String email;
        private String username;

        @Builder
        public AccountRes(Account account){
            this.email=account.getEmail();
            this.username=account.getUsername();
        }
    }
}
