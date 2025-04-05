package com.newspaper.newspaper.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entity){
        super("The " + entity.getSimpleName().toLowerCase() + " does not exist");
    }

}
