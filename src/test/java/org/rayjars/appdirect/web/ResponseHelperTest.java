package org.rayjars.appdirect.web;

import org.junit.Test;
import org.rayjars.appdirect.xml.beans.Result;

import static org.assertj.core.api.Assertions.assertThat;


public class ResponseHelperTest {
    @Test
    public void shouldHaveError() throws Exception {
        Result result = ResponseHelper.error("Cannot communicate with the server", "UNKNOWN_ERROR");


        assertThat(result.isSuccess()).isEqualTo(false);
        assertThat(result.getErrorCode()).isEqualTo("UNKNOWN_ERROR");
        assertThat(result.getMessage()).isEqualTo("Cannot communicate with the server");

    }

    @Test
    public void shouldHaveSuccess() throws Exception {
        Result result = ResponseHelper.success("Subscription updated");

        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getErrorCode()).isNull();
        assertThat(result.getMessage()).isEqualTo("Subscription updated");
    }

    @Test
    public void shouldHaveSuccessWithAccountIdentifier() throws Exception {
        Result result = ResponseHelper.success("Subscription created", "1234");

        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getErrorCode()).isNull();
        assertThat(result.getMessage()).isEqualTo("Subscription created");
        assertThat(result.getAccountIdentifier()).isEqualTo("1234");

    }
}
