package org.rayjars.appdirect.web;

import org.rayjars.appdirect.xml.beans.Result;


public final class ResponseHelper {

    private ResponseHelper() {
    }

    public static Result error(String message, String errorCode) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        result.setErrorCode(errorCode);
        return result;

    }

    public static Result success(String message) {
        return success(message, null);
    }


    public static Result success(String message, String accountId) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage(message);

        if (accountId != null) {
            result.setAccountIdentifier(accountId);
        }
        return result;
    }


}