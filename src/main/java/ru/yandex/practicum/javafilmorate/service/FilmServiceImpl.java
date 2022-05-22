package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.util.exception.StorageOperationException;

import java.util.*;

import static ru.yandex.practicum.javafilmorate.util.util.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    private final Map<Integer, Set<Integer>> usersByFilm = new HashMap<>();
    private final int POPULAR_FILMS_DEFAULT_SIZE = 10;

    @Override
    public Film create(Film film) {
        if(film.getId() != 0)
            throw new StorageOperationException(SAVED_FILM_STATUS_ERROR);

        try {
            filmStorage.save(film);
        } catch (Exception e) {
            throw new StorageOperationException(FILM_CREATION_ERROR);
        }

        usersByFilm.putIfAbsent(film.getId(), new HashSet<>());

        return film;
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(filmStorage.findById(id));
    }

    @Override
    public Collection<Film> getAll() {
        return filmStorage.findAll();
    }

    @Override
    public void addLike(int id, int userId) {
        checkFilmOperation(id, userId);

        usersByFilm.get(id).add(userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        checkFilmOperation(id, userId);

        usersByFilm.get(id).remove(userId);
    }

    private void checkFilmOperation(int id, int userId) {
        getById(id).orElseThrow();
        userService.getById(userId).orElseThrow();
    }

    @Override
    public Collection<Film> getPopular(Optional<Integer> count) {
        List<Integer> popularFilmIds = getPopularFilms();

        int popularFilmsSize = count.orElse(POPULAR_FILMS_DEFAULT_SIZE);

        List<Film> result = new ArrayList<>();
        for (int i = 0; i < popularFilmIds.size() && i < popularFilmsSize; i++)
            result.add(getById(popularFilmIds.get(i)).orElseThrow());
        return result;
    }

    private List<Integer> getPopularFilms() {
        List<Map.Entry<Integer, Set<Integer>>> list = new ArrayList<>(usersByFilm.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().size() != o2.getValue().size())
                return -1 * Integer.compare(o1.getValue().size(), o2.getValue().size());
            else
                return Integer.compare(o1.getKey(), o2.getKey());
        });

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public void update(Film film) {
        if(film.getId() == 0)
            throw new StorageOperationException(UPDATED_FILM_STATUS_ERROR);

        getById(film.getId()) .orElseThrow();

        try {
            filmStorage.save(film);
        } catch (Exception e) {
            throw new StorageOperationException(FILM_UPDATE_ERROR);
        }
    }

    @Override
    public void delete(int id) {
        try {
            filmStorage.deleteById(id);
        } catch (Exception e) {
            throw new StorageOperationException(USER_DELETION_ERROR);
        }

        usersByFilm.remove(id);
    }

}
