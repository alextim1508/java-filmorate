package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.MPARating;
import ru.yandex.practicum.javafilmorate.service.interfaces.MPARatingService;
import ru.yandex.practicum.javafilmorate.storage.interfaces.MPARatingStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MPARatingServiceImpl implements MPARatingService {

    private final MPARatingStorage mpaRatingStorage;

    @Override
    public Optional<MPARating> getById(int id) {
        return Optional.ofNullable(mpaRatingStorage.findById(id));
    }

    @Override
    public Collection<MPARating> getAll() {
        return mpaRatingStorage.findAll();
    }
}
