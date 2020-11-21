package pl.sda.springtrainingjavalub22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.sda.springtrainingjavalub22.config.CompanyInfo;
import pl.sda.springtrainingjavalub22.config.SmtpProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {CompanyInfo.class, SmtpProperties.class})
@EnableScheduling
public class SpringTrainingJavalub22Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringTrainingJavalub22Application.class, args);
    }

}
