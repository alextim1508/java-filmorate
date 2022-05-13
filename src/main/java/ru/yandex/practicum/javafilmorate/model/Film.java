package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import ru.yandex.practicum.javafilmorate.util.validator.After;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

    private int id;

    @NotBlank
    private String name;

    @Size(min =1, max = 20)
    @NotBlank
    private String description;

    @After("1895-12-28")
    private LocalDate releaseDate;

    @DurationMin(seconds = 1)
    private Duration duration;
}
