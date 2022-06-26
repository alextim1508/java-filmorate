package ru.yandex.practicum.javafilmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.MPARating;
import ru.yandex.practicum.javafilmorate.storage.interfaces.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film save(Film film) {
        String sqlQuery =
                "insert into films(name, description, release_date, duration, rate, mpa_id) " +
                        "values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, new String[]{"id"});
                    preparedStatement.setString(1, film.getName());
                    preparedStatement.setString(2, film.getDescription());
                    preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
                    preparedStatement.setLong(4, film.getDuration().getSeconds());
                    preparedStatement.setInt(5, film.getRate());
                    preparedStatement.setInt(6, film.getMpa().getId());
                    return preparedStatement;
                }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Column \"id\" doesn't exist");
        }

        film.setId(key.intValue());

        return film;
    }

    @Override
    public Film findById(int id) {
        String sqlQuery = "select id, name, description, release_date, duration, rate, mpa_id from films where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
    }

    @Override
    public Collection<Film> findAll() {
        String sqlQuery = "select id, name, description, release_date, duration, rate, mpa_id from films";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public int update(Film film) {
        String sqlQuery =
                "update films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, rate = ?, mpa_id = ? " +
                "where id = ?";

        return jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration().getSeconds(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("delete from films where id = ?", id) > 0;
    }

    @Override
    public List<Film> getPopular(int limit) {
        String sqlQuery =
                "select id, count(l.user_id) c from films f left join likes l on f.id = l.film_id " +
                "group by id " +
                "order by c desc " +
                "limit ?";

        List<Integer> filmIds = jdbcTemplate.query(sqlQuery,
                (resultSet, rowNum) -> resultSet.getInt("id"), limit);

        List<Film> films = new ArrayList<>();
        for (int id : filmIds) {
            films.add(findById(id));
        }

        return films;
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(Duration.ofSeconds(resultSet.getLong("duration")))
                .rate(resultSet.getInt("rate"))
                .mpa(MPARating.builder().id(resultSet.getInt("mpa_id")).build())
                .build();
    }
}
