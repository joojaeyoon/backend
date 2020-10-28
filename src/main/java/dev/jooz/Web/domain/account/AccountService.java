package dev.jooz.Web.domain.account;

import dev.jooz.Web.util.PasswordEncoding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account save(AccountDto.CreateReq dto){
        return accountRepository.save(dto.toEntity());
    }

    public boolean existsByEmail(String email){
        return accountRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    public Account findById(Long id){
        Optional<Account> account=accountRepository.findById(id);
        account.orElseThrow(()-> new NoSuchElementException());
        return account.get();
    }

    public boolean loginAccount(AccountDto.LoginReq dto){
        PasswordEncoding passwordEncoding=new PasswordEncoding();

        Optional<Account> account=accountRepository.findByUsername(dto.getUsername());
        account.orElseThrow(()->new NoSuchElementException());

        if(passwordEncoding.matches(dto.getPassword(),account.get().getPassword()))
            return true;
        return false;
    }
}
