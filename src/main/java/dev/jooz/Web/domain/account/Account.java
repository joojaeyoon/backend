package dev.jooz.Web.domain.account;


import dev.jooz.Web.domain.AuditorEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name="account")
public class Account extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Builder
    public Account(String username, String password,AccountRole role){
        this.username=username;
        this.password=password;
        this.role=role;
    }

}
