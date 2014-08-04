package org.rayjars.appdirect.exceptions;

public class OperationCanceledException extends AppException {

    public OperationCanceledException( String message) {
        super("OPERATION_CANCELED", message);
    }
}
