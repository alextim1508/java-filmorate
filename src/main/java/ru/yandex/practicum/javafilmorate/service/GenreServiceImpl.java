package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.service.interfaces.GenreService;
import ru.yandex.practicum.javafilmorate.storage.interfaces.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Optional<Genre> getById(int id) {
        return Optional.ofNullable(genreStorage.findById(id));
    }

    @Override
    public Collection<Genre> getAll() {
        return genreStorage.findAll();
    }
}
