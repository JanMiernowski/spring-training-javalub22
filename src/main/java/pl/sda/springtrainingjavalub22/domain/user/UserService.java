package pl.sda.springtrainingjavalub22.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.sda.springtrainingjavalub22.domain.email.Email;
import pl.sda.springtrainingjavalub22.domain.email.EmailRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;

    public void register(User user) {
        user.encodePassword(passwordEncoder);

        userRepository.create(user);

        sendWelcomeEmail(user);
    }

    private void sendWelcomeEmail(User user) {
        Set<String> attachments = new HashSet<>();
        attachments.add("attachment/regulamin.pdf");

        emailRepository.sendEmail(
                new Email(user.getUsername(),
                        "Witamy w wypożyczalni",
                        prepareWelcomeMail(user.getUsername()),
                        attachments));
    }

    private String prepareWelcomeMail(String username) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username);

        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process("/email/welcome", context);
    }
}
