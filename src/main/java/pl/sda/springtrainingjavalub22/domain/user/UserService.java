package pl.sda.springtrainingjavalub22.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.springtrainingjavalub22.domain.email.Email;
import pl.sda.springtrainingjavalub22.domain.email.EmailRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;

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
                        "Witaj w naszej wypożyczalni samochodów SDA.",
                        attachments));
    }
}
