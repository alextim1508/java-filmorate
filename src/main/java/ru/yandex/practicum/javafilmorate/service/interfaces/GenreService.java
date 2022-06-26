package ru.yandex.practicum.javafilmorate.service.interfaces;

import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> getById(int id);

    Collection<Genre> getAll();
}
