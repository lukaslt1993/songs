package com.github.lukaslt1993.songs.exception;

public class EntityNotFoundException extends EntityException {

    public EntityNotFoundException(String message, Object id) {
        super(message, id);
    }
}
