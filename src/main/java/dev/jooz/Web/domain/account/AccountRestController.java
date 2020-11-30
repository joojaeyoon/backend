package dev.jooz.Web.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return accountService.save(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.AccountRes loginAccount(@RequestBody @Valid final AccountDto.LoginReq dto){
        return accountService.loginAccount(dto);
    }
}
