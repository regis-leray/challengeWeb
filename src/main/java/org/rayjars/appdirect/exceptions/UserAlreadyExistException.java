package org.rayjars.appdirect.exceptions;

/**
 * Created by regis on 14-07-31.
 */
public class UserAlreadyExistException extends AppException {

    public UserAlreadyExistException(String message) {
        super("USER_ALREADY_EXISTS", message);
    }
}
