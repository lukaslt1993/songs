package com.github.lukaslt1993.songs.exception;

public class EntityCreationException extends EntityException {

    public EntityCreationException(String message, Object id) {
        super(message, id);
    }
}
