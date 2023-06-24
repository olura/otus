package ru.otus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;

@Configuration
public class ApplicationConfiguration {
    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }
}
