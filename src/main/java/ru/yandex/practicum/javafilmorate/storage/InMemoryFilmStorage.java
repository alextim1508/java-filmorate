package ru.yandex.practicum.javafilmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> storage;
    private int id;

    public InMemoryFilmStorage() {
        this.storage = new HashMap<>();
    }

    public InMemoryFilmStorage(Map<Integer, Film> storage) {
        this.storage = storage;
    }

    @Override
    public Film save(Film film) {

        if (film.getId() == 0) {
            film.setId(++id);
            storage.put(id, film);
        } else {
            storage.put(id, film);
        }

        return film;
    }

    @Override
    public Film findById(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<Film> findAll() {
        return storage.values();
    }

    @Override
    public int update(Film film) {
        if(storage.containsKey(film.getId())) {
            storage.put(film.getId(), film);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean deleteById(int id) {
        return storage.remove(id) != null;
    }

    @Override
    public List<Film> getPopular(int limit) {
        return null;
    }
}
