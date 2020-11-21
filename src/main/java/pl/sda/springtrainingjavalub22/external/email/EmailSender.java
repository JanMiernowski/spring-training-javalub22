package pl.sda.springtrainingjavalub22.external.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import pl.sda.springtrainingjavalub22.config.SmtpProperties;
import pl.sda.springtrainingjavalub22.domain.email.Email;
import pl.sda.springtrainingjavalub22.domain.email.EmailRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class EmailSender implements EmailRepository {

    private final SmtpProperties smtpProperties;

    @Override
    public void sendEmail(Email email) {
        try {
            Message msg = new MimeMessage(createSession());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getSendTo(), false));
            msg.setSubject(email.getTitle());
            msg.setFrom(new InternetAddress(smtpProperties.getFrom(), false));

            MimeBodyPart content = new MimeBodyPart();
            content.setContent(email.getContent(), "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(content);

            if (email.getAttachments() != null && !email.getAttachments().isEmpty()) {
                for (String path : email.getAttachments()) {
                    MimeBodyPart attachment = new MimeBodyPart();
                    attachment.attachFile(ResourceUtils.getFile("classpath:" + path));
                    multipart.addBodyPart(attachment);
                }
            }

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private Properties prepareProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", smtpProperties.getAuth().toString());
        properties.put("mail.smtp.starttls.enable", smtpProperties.getStarttls().toString());
        properties.put("mail.smtp.host", smtpProperties.getHost());
        properties.put("mail.smtp.port", smtpProperties.getPort().toString());
        return properties;
    }

    private Session createSession() {
        return Session.getInstance(prepareProperties(),
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpProperties.getUser(), smtpProperties.getPassword());
                }
            });
    }
}
