package ru.otus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;

@Configuration
@ComponentScan("ru.otus")
@PropertySource("application.property")
public class ApplicationConfiguration {
    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }
}
