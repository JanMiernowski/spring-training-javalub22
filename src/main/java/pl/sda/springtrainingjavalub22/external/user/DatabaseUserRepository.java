package pl.sda.springtrainingjavalub22.external.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.springtrainingjavalub22.domain.user.User;
import pl.sda.springtrainingjavalub22.domain.user.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void create(User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(false)
                .build();
        jpaUserRepository.save(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(ent -> new User(ent.getUsername(), ent.getPassword(), ent.getRole(), ent.getEnabled()));
    }

    @Override
    public void activate(String username) {
        jpaUserRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.activate();
                    jpaUserRepository.save(user);
                });
    }
}
