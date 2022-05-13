package ru.yandex.practicum.javafilmorate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.javafilmorate.util.validator.AfterValidator;
import ru.yandex.practicum.javafilmorate.util.validator.MinValidator;

@Configuration
public class Config {

    @Bean
    public AfterValidator afterValidator() {
        return new AfterValidator();
    }

    @Bean
    public MinValidator minValidator() {
        return new MinValidator();
    }


}
