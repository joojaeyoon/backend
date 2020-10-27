package dev.jooz.Web.domain.account;

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

    public Account findById(Long id){
        Optional<Account> account=accountRepository.findById(id);
        account.orElseThrow(()-> new NoSuchElementException());
        return account.get();
    }
}
