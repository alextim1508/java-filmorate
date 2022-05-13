package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.util.exception.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor @Slf4j
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int id;

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film, BindingResult result) {
        if(result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        film.setId(id);
        films.put(id, film);
        log.info("Film {} is created", film);
        id++;
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        films.put(film.getId(), film);
        log.info("Film {} is updated", film);
        return film;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
