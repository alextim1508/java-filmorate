package ru.yandex.practicum.javafilmorate.storage.interfaces;

import java.util.List;

public interface FilmGenreStorage {

    void save(int filmId, int genreId);

    List<Integer> findGenreIdsByFilm(int filmId);

    void deleteByFilmId(int filmId);
}
