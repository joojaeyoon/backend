package dev.jooz.Web.domain.account;

import dev.jooz.Web.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountDto.AccountRes createAccount(@RequestBody @Valid final AccountDto.CreateReq dto){

        // TODO 중복가입 예외처리
        return new AccountDto.AccountRes(accountService.save(dto));
    }
}
