package ru.yandex.practicum.javafilmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FilmGenreStorage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(int filmId, int genreId) {
        String sqlQuery = "insert into films_genres(film_id, genre_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public List<Integer> findGenreIdsByFilm(int filmId) {
        List<Integer> genreIds = jdbcTemplate.query("select genre_id from films_genres where film_id = ?",
                (rs, rowNum) -> rs.getInt("genre_id"), filmId);
        return genreIds;
    }

    @Override
    public void deleteByFilmId(int filmId) {
        String sqlQuery = "delete from films_genres where film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }
}
