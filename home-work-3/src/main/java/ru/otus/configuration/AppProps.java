package ru.otus.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app")
public record AppProps(String file, int rightAnswers, Locale locale) {
}
