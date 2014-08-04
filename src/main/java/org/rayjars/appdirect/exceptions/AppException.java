package org.rayjars.appdirect.exceptions;

/**
 * Created by regis on 14-07-31.
 */
public abstract class AppException extends Exception {

    private final String code;

    protected AppException(String code, String message) {
        super(message);
        this.code = code;

    }

    public String getCode() {
        return code;
    }
}
