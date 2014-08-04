package org.rayjars.appdirect.exceptions;

/**
 * Created by regis on 14-07-31.
 */
public class UserNotFoundException extends AppException {
    public UserNotFoundException(String message) {
        super("USER_NOT_FOUND", message);
    }
}
