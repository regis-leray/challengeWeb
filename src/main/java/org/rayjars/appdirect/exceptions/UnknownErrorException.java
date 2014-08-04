package org.rayjars.appdirect.exceptions;

public class UnknownErrorException extends AppException {

    public UnknownErrorException(String message) {
        super("UNKNOWN_ERROR", message);
    }
}
