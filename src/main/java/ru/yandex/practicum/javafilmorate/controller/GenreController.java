package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.service.interfaces.GenreService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public Genre getById(@PathVariable int id) {
        log.info("GetById {}", id);
        Optional<Genre> genre = genreService.getById(id);
        log.info("Answer {}", genre);
        return genre.orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @GetMapping
    public Collection<Genre> getAll() {
        log.info("GetAll");
        Collection<Genre> genres = genreService.getAll();
        log.info("Answer {}", genres);
        return genres;
    }
}
