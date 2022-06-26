package ru.yandex.practicum.javafilmorate.storage.interfaces;

import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {

    Genre findById(int id);

    Collection<Genre> findAll();
}
