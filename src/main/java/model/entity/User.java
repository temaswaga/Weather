package model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login", length = 15)
    private String login;

    @Column(name = "password", length = 70)
    private String password;

    public User(String password, String login) {
        this.password = password;
        this.login = login;
    }
}