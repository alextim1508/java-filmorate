package ru.yandex.practicum.javafilmorate.storage.interfaces;

import ru.yandex.practicum.javafilmorate.model.MPARating;

import java.util.Collection;

public interface MPARatingStorage {

    MPARating findById(int id);

    Collection<MPARating> findAll();
}
