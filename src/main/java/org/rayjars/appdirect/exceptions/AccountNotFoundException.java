package org.rayjars.appdirect.exceptions;


public class AccountNotFoundException extends AppException {

    public AccountNotFoundException(String message) {
        super("ACCOUNT_NOT_FOUND", message);
    }
}
