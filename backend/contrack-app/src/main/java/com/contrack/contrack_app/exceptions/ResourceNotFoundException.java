package com.contrack.contrack_app.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " com ID " + id + " não encontrado(a).");
    }
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}