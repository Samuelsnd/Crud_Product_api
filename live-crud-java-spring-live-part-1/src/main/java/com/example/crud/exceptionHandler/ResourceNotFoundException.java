package com.example.crud.exceptionHandler;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String ex){
        super(ex);
    }
}
