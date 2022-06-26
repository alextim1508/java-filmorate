package ru.yandex.practicum.javafilmorate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.javafilmorate.util.validator.AfterValidator;

@Configuration
public class Config {

    @Bean
    public AfterValidator afterValidator() {
        return new AfterValidator();
    }

}
