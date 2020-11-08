package pl.sda.springtrainingjavalub22.domain.email;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailTask {

    private final EmailRepository emailRepository;

    @Scheduled(fixedDelay = 10000)
    public void sendEmails() {
        emailRepository.sendEmail("me@wp.pl", "bla bla");
    }

}
