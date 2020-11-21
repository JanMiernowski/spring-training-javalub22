package pl.sda.springtrainingjavalub22.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "email.smtp")
public class SmtpProperties {
    private Boolean auth = true;
    private Boolean starttls = true;
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String from;
}