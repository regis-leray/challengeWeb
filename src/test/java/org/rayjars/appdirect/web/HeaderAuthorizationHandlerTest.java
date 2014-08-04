package org.rayjars.appdirect.web;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rayjars.appdirect.OAuth;
import org.rayjars.appdirect.xml.XmlWriter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
public class HeaderAuthorizationHandlerTest {

    HeaderAuthorizationHandler handler = new HeaderAuthorizationHandler();
    private MockHttpServletRequest request;

    @Before
    public void createRequest(){
        request = new MockHttpServletRequest();
        handler.setoAuth(new OAuth());
        handler.setWriter(new XmlWriter());
    }




    @Test
    public void shouldPreHandleFail() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertThat(handler.preHandle(request, response, new Object())).isFalse();

        assertThat(response.getContentType()).contains("application/xml");
        assertThat(response.getContentAsString()).contains("<errorCode>UNAUTHORIZED</errorCode>");
        assertThat(response.getContentAsString()).contains("<success>false</success>");

    }
}