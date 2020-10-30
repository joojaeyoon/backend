package dev.jooz.Web.domain.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void load_account(){
        String email="test@gmail.com";
        String username="testuser";
        String password="supersecret";

        accountRepository.save(Account.builder()
                                        .email(email)
                                        .username(username)
                                        .password(password)
                                        .build());

        List<Account> accountList=accountRepository.findAll();

        Account account=accountList.get(0);

        assertEquals(email,account.getEmail());
        assertEquals(username,account.getUsername());
    }
}
