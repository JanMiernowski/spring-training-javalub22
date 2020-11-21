package pl.sda.springtrainingjavalub22.domain.user.token;

import java.util.Optional;

public interface TokenRepository {
    Token generateForUser(String user);
    Optional<Token> getByToken(String token);
}
