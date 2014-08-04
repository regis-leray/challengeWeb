package org.rayjars.appdirect.exceptions;

public class MaxUsersReachedException extends AppException {

    public MaxUsersReachedException(String message) {
        super("MAX_USERS_REACHED", message);
    }
}
