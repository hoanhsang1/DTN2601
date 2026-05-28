package org.example.dto;

public class ImportError {
    private final String line;
    private final String message;

    public ImportError(String line, String message) {
        this.line = line;
        this.message = message;
    }

    public String getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }
}