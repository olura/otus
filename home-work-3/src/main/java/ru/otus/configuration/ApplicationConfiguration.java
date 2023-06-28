package ru.otus.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class ApplicationConfiguration {

    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }
}
