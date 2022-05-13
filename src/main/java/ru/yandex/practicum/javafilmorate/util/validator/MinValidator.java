package ru.yandex.practicum.javafilmorate.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.LocalDate;

public class MinValidator implements ConstraintValidator<Min, Duration> {

    private long date;

    public void initialize(After annotation) {
        date = Duration.parse(annotation.value()).getSeconds();
    }

    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        boolean valid = true;
        if (value != null) {
            if (value.getSeconds() < date) {
                valid = false;
            }
        }
        return valid;
    }
}