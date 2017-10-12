package ru.ncedu.vladislav_verzhbitski.archivecomparator.ArgsParser;

import java.io.IOException;

public class FileFoundException extends IOException {
    public FileFoundException() {
    }

    public FileFoundException(String message) {
        super(message);
    }

    public FileFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFoundException(Throwable cause) {
        super(cause);
    }
}
