package com.project.wab.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author "Vladyslav Paun"
 */
@Configuration
public class SendGridConfig {
    @Value("${spring.sendgrid.api-key}")
    String sendGridAPIKey;

    @Bean
    public SendGrid getSendGridWithAPIKey() {
        return new SendGrid(sendGridAPIKey);
    }
}
