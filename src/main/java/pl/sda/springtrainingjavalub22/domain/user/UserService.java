package pl.sda.springtrainingjavalub22.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.sda.springtrainingjavalub22.domain.email.Email;
import pl.sda.springtrainingjavalub22.domain.email.EmailRepository;
import pl.sda.springtrainingjavalub22.domain.user.token.Token;
import pl.sda.springtrainingjavalub22.domain.user.token.TokenRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;
    private final TokenRepository tokenRepository;

    public boolean activate(String token) {
        Optional<Token> foundedToken = tokenRepository.getByToken(token)
                .filter(tok -> tok.getValidTo().isAfter(LocalDateTime.now()));

        if (!foundedToken.isPresent()) {
            return false;
        }

        foundedToken.ifPresent(tok -> userRepository.activate(tok.getUsername()));

        return true;
    }

    public void register(User user) {
        user.encodePassword(passwordEncoder);

        userRepository.create(user);

        Token token = tokenRepository.generateForUser(user.getUsername());

        sendActivationEmail(user, token);
    }


    private void sendActivationEmail(User user, Token token) {
        Set<String> attachments = new HashSet<>();
        attachments.add("attachment/regulamin.pdf");

        emailRepository.sendEmail(
                new Email(user.getUsername(),
                        "Witamy w wypożyczalni",
                        prepareActivationMail(user.getUsername(), token.getToken(),
                                token.getValidTo().toString()),
                        attachments));
    }

    private String prepareActivationMail(String username, String token, String validTo) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username);
        variables.put("token", token);
        variables.put("validTo", validTo);

        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process("/email/activation", context);
    }
}
