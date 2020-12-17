package dev.jooz.Web.domain.account;

import dev.jooz.Web.exception.account.PasswordNotMatchException;
import dev.jooz.Web.exception.account.UserNotExistException;
import dev.jooz.Web.util.RedisUtil;
import dev.jooz.Web.exception.account.UsernameExistsException;
import dev.jooz.Web.util.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    public AccountDto.AccountRes save(AccountDto.CreateReq dto){
        if(existsByUsername(dto.getUsername()))
            throw new UsernameExistsException(dto.getUsername());

        PasswordEncoding passwordEncoding=new PasswordEncoding();
        dto.setPassword(passwordEncoding.encode(dto.getPassword()));
        dto.setRole(AccountRole.ROLE_USER);

        final String token=jwtUtil.generateToken(dto.getUsername());
        final String refreshJwt=jwtUtil.generateRefreshToken(dto.getUsername());

        redisUtil.setDataExpire(token,dto.getUsername(),jwtUtil.TOKEN_VALIDATION_SECOND);
        redisUtil.setDataExpire(refreshJwt,dto.getUsername(),jwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

        Account account=accountRepository.save(dto.toEntity());

        return new AccountDto.AccountRes(account.getUsername(),token,refreshJwt);
    }

    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    public Account findById(Long id){
        Optional<Account> account=accountRepository.findById(id);
        account.orElseThrow(()-> new NoSuchElementException());
        return account.get();
    }

    public Account findByUsername(String username){
        Optional<Account> accountOptional=accountRepository.findByUsername(username);

        accountOptional.orElseThrow(()->new UserNotExistException(username));

        return accountOptional.get();
    }

    public AccountDto.AccountRes loginAccount(AccountDto.LoginReq dto){
        PasswordEncoding passwordEncoding=new PasswordEncoding();

        Optional<Account> account=accountRepository.findByUsername(dto.getUsername());
        account.orElseThrow(()->new NoSuchElementException());

        String passwordA=dto.getPassword();
        String passwordB=account.get().getPassword();
        if(!passwordEncoding.matches(passwordA,passwordB))
            throw new PasswordNotMatchException();

        final String token=jwtUtil.generateToken(dto.getUsername());
        final String refreshJwt=jwtUtil.generateRefreshToken(dto.getUsername());

        redisUtil.setDataExpire(token,dto.getUsername(),jwtUtil.TOKEN_VALIDATION_SECOND);
        redisUtil.setDataExpire(refreshJwt,dto.getUsername(),jwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

        // TODO 로그인 할 때랑 가입 할 때 토큰이 따로따로 생기는거 방지하기

        return new AccountDto.AccountRes(dto.getUsername(),token,refreshJwt);
    }
}
