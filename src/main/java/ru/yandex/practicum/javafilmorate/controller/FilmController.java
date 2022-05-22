package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.util.exception.ValidationException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film, BindingResult result) {
        if (result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        filmService.create(film);
        log.info("Film {} is created", film);

        return film;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        return filmService.getById(id).orElseThrow();
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
        log.info("Like is added to film with id {} by user with id {}", id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
        log.info("Like is removed to film with id {} by user with id {}", id, userId);
    }

    @GetMapping("popular")
    public Collection<Film> getPopular(@RequestParam Optional<Integer> count) {
        return filmService.getPopular(count);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film, BindingResult result) {
        if(result.getErrorCount() != 0) {
            log.error("{}", result.getAllErrors());
            throw new ValidationException("ValidationException");
        }

        filmService.update(film);
        log.info("Film {} is updated", film);

        return film;
    }
}
