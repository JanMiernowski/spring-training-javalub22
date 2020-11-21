package pl.sda.springtrainingjavalub22.external.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.springtrainingjavalub22.domain.email.Email;
import pl.sda.springtrainingjavalub22.domain.email.EmailRepository;

@Component
@Profile("!dev")
public class MockEmailSender implements EmailRepository {
    @Override
    public void sendEmail(Email email) {
        System.out.println("\n\nMock do wysylania maili\n\n");
    }
}
