package pl.sda.springtrainingjavalub22.external.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100, name = "login")
    private String username;

    private String password;

    private String role;

    private Boolean enabled = false;

    public void activate() {
        enabled = true;
    }
}
