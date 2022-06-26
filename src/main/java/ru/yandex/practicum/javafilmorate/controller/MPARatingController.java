package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.MPARating;
import ru.yandex.practicum.javafilmorate.service.interfaces.MPARatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MPARatingController {

    private final MPARatingService mpaRatingService;

    @GetMapping("/{id}")
    public MPARating getById(@PathVariable int id) {
        return mpaRatingService.getById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @GetMapping
    public Collection<MPARating> getAll() {
        return mpaRatingService.getAll();
    }
}
