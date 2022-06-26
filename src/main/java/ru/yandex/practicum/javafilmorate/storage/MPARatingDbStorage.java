package ru.yandex.practicum.javafilmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.MPARating;
import ru.yandex.practicum.javafilmorate.storage.interfaces.MPARatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MPARatingDbStorage implements MPARatingStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public MPARating findById(int id) {
        String sqlQuery = "select id, name from mpa where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMPARating, id);
    }

    @Override
    public Collection<MPARating> findAll() {
        String sqlQuery = "select id, name from mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMPARating);
    }

    private MPARating mapRowToMPARating(ResultSet resultSet, int rowNum) throws SQLException {
        return MPARating.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
