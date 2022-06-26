package ru.yandex.practicum.javafilmorate.service.interfaces;

import ru.yandex.practicum.javafilmorate.model.MPARating;

import java.util.Collection;
import java.util.Optional;

public interface MPARatingService {

    Optional<MPARating> getById(int id);

    Collection<MPARating> getAll();
}
