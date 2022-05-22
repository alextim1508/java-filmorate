package ru.yandex.practicum.javafilmorate.util.exception;

public class StorageOperationException extends RuntimeException {
    public StorageOperationException(Exception exception) {
        super(exception);
    }

    public StorageOperationException(String message) {
        super(message);
    }
}
