package dev.jooz.Web.domain.account;

import dev.jooz.Web.exception.account.EmailExistException;
import dev.jooz.Web.exception.account.UsernameExistsException;
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
        if(accountService.existsByEmail(dto.getEmail())){
            throw new EmailExistException(dto.getEmail());
        }
        if(accountService.existsByUsername(dto.getUsername())){
            throw new UsernameExistsException(dto.getUsername());
        }
        return new AccountDto.AccountRes(accountService.save(dto));
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean loginAccount(@RequestBody @Valid final AccountDto.LoginReq dto){

        if(accountService.loginAccount(dto))
            return true;
        return false;
    }
}
