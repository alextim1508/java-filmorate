package ru.yandex.practicum.javafilmorate.storage.interfaces;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film save(Film film);

    Film findById(int id);

    Collection<Film> findAll();

    int update(Film film);

    boolean deleteById(int id);

    List<Film> getPopular(int limit);
}
