package com.jimwhere.api;

import com.jimwhere.api.payment.properties.TossPaymentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(TossPaymentProperties.class)
@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
public class JimwhereApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JimwhereApiApplication.class, args);
    }

}
