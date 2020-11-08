package pl.sda.springtrainingjavalub22.domain.email;

public interface EmailRepository {
    void sendEmail(String to, String content);
}
