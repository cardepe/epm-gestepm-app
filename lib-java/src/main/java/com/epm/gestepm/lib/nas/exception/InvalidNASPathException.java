package com.epm.gestepm.lib.nas.exception;

public class InvalidNASPathException extends RuntimeException {

    private final String path;

    public InvalidNASPathException(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
