package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import ru.yandex.practicum.javafilmorate.util.util.CustomDurationSerializer;
import ru.yandex.practicum.javafilmorate.util.validator.After;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {

    private int id;

    @NotBlank
    private String name;

    @Size(min =1, max = 200)
    @NotBlank
    private String description;

    @After("1895-12-28")
    private LocalDate releaseDate;

    @DurationMin(seconds = 1)
    @JsonSerialize(using = CustomDurationSerializer.class)
    private Duration duration;

    private int rate;

    private Set<Genre> genres;

    private MPARating mpa;
}