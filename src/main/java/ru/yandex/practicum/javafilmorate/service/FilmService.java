package ru.yandex.practicum.javafilmorate.service;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Film create(Film film);

    Optional<Film> getById(int id);

    Collection<Film> getAll();

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);

    Collection<Film> getPopular(Optional<Integer> count);

    void update(Film user);

    void delete(int id);
}
