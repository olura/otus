package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.configuration.LocaleProvider;

@Component
public class LocalizationService {
    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public LocalizationService(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    public String getMessage(String message, String ... args) {
        return messageSource.getMessage(message, args, localeProvider.getLocale());
    }
}
