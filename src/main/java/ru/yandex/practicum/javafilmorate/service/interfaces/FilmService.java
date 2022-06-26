package ru.yandex.practicum.javafilmorate.service.interfaces;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Film create(Film film);

    Optional<Film> getById(int id);

    Collection<Film> getAll();

    void update(Film user);

    void delete(int id);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);

    Collection<Film> getPopular(Optional<Integer> count);
}
