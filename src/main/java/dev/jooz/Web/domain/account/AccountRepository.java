package dev.jooz.Web.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByEmail(String email);
    boolean existsByEmail(String email);
}
