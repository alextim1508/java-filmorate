package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film save(Film film);

    Film findById(int id);

    Collection<Film> findAll();

    void deleteById(int id);
}
