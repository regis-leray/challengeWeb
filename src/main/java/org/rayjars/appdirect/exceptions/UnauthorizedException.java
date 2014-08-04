package org.rayjars.appdirect.exceptions;

/**
 * Created by regis on 14-07-31.
 */
public class UnauthorizedException extends AppException {

    public UnauthorizedException(String message) {
        super("UNAUTHORIZED",message );
    }
}
