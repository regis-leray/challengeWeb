package org.rayjars.appdirect.exceptions;

public class ConfigurationErrorException extends AppException {

    public ConfigurationErrorException(String message) {
        super("CONFIGURATION_ERROR",message);
    }
}
