package dev.jooz.Web.domain.account;

import dev.jooz.Web.domain.account.exception.EmailExistException;
import dev.jooz.Web.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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

        if(accountService.existsByEmail(dto.getEmail())){
            throw new EmailExistException(dto.getEmail());
        }
        return new AccountDto.AccountRes(accountService.save(dto));
    }
}
