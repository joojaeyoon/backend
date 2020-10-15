package dev.jooz.Web.domain.account;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Builder
    public Account(String email, String username, String password){
        this.email=email;
        this.username=username;
        this.password=password;
    }

}
