package org.rayjars.appdirect.web;

import org.junit.Test;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.beans.Result;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandlerTest {

    private ErrorHandler handler = new ErrorHandler();

    @Test
    public void shouldHandleValidationError() {
        Result result = handler.handleValidationError(new UnknownErrorException("Cannot communicate with the server"));

        assertThat(result.isSuccess()).isEqualTo(false);
        assertThat(result.getErrorCode()).isEqualTo("UNKNOWN_ERROR");
        assertThat(result.getMessage()).isEqualTo("Cannot communicate with the server");
    }
}