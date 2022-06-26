package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPARating;
import ru.yandex.practicum.javafilmorate.service.interfaces.FilmService;
import ru.yandex.practicum.javafilmorate.service.interfaces.GenreService;
import ru.yandex.practicum.javafilmorate.service.interfaces.MPARatingService;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FilmGenreStorage;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.interfaces.LikeStorage;
import ru.yandex.practicum.javafilmorate.util.exception.StorageOperationException;

import java.util.*;

import static ru.yandex.practicum.javafilmorate.util.util.ErrorMessage.*;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final FilmGenreStorage filmGenreStorage;

    private final GenreService genreService;
    private final MPARatingService mpaRatingService;

    private final int POPULAR_FILM_COUNT_DEFAULT = 10;

    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                           LikeStorage likeStorage,
                           FilmGenreStorage filmGenreStorage,
                           GenreService genreService,
                           MPARatingService mpaRatingService) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.genreService = genreService;
        this.mpaRatingService = mpaRatingService;
    }

    @Override
    public Film create(Film film) {
        try {
            filmStorage.save(film);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new StorageOperationException(FILM_CREATION_ERROR, e);
        }

        if (film.getGenres() != null) {
            for(Genre genre : film.getGenres())
                filmGenreStorage.save(film.getId(), genre.getId());
        }

        return film;
    }

    @Override
    public Optional<Film> getById(int id) {
        Optional<Film> film = Optional.ofNullable(filmStorage.findById(id));
        if(film.isPresent()) {

            MPARating mpa = film.get().getMpa();
            if(mpa != null) {
                mpaRatingService.getById(mpa.getId()).ifPresent(mpaRating ->
                        film.get().setMpa(mpaRating));
            }

            List<Integer> genreIds = filmGenreStorage.findGenreIdsByFilm(id);
            if(!genreIds.isEmpty()) {
                Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));
                for(int i: genreIds)
                    genreService.getById(i).ifPresent(genres::add);
                film.get().setGenres(genres);
            } else {
                film.get().setGenres(null);
            }
        }

        return film;
    }

    @Override
    public Collection<Film> getAll() {
        Collection<Film> films = filmStorage.findAll();

        for (Film film : films) {
            List<Integer> genreIds = filmGenreStorage.findGenreIdsByFilm(film.getId());

            genreIds.stream().map(genreId ->
                    Genre.builder().id(genreId).build()).forEach(genre -> film.getGenres().add(genre));

            if(genreIds.isEmpty())
                film.setGenres(null);
        }
        return films;
    }

    @Override
    public void update(Film film) {
        if(filmStorage.update(film) == 0)
            throw new EmptyResultDataAccessException(1);

        filmGenreStorage.deleteByFilmId(film.getId());

        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres())
                filmGenreStorage.save(film.getId(), genre.getId());

            Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));
            genres.addAll(film.getGenres());
            film.setGenres(genres);
        }
    }

    @Override
    public void delete(int id) {
        if(!filmStorage.deleteById(id)) {
            throw new EmptyResultDataAccessException(1);
        }

        filmGenreStorage.deleteByFilmId(id);

        likeStorage.removeAllLikesByFilmId(id);
    }

    @Override
    public void addLike(int id, int userId) {
        likeStorage.addLike(id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        if(!likeStorage.removeLike(id, userId)) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public Collection<Film> getPopular(Optional<Integer> count) {
        return filmStorage.getPopular(count.orElse(POPULAR_FILM_COUNT_DEFAULT));
    }
}
