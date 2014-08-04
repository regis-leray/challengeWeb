package org.rayjars.appdirect;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rayjars.appdirect.exceptions.UnauthorizedException;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URL;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rayjars.appdirect.TestHelper.splitQuery;

public class OAuthTest {

    OAuth oAuth = new OAuth();


    @Before
    public void setKeyAndSecret(){

        oAuth.setConsumerKey("rayproduct-11365");
        oAuth.setConsumerSecret("rHLVoAjt0lflx9ZX");

    }

    @Test
    public void shouldSignUrl() throws Exception {
        String urlSigned =  oAuth.signUrl("http://www.appdirect.com/AppDirect/rest/api/events/dummyChange");

        assertThat(urlSigned).contains("oauth_consumer_key=rayproduct-11365");
        assertThat(urlSigned).contains("oauth_signature");
    }

    @Ignore("Sometimes if failed / sometime not ")
    @Test
    public void shouldValidate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/subscription/order");
        request.setServerName("localhost");
        request.setRemoteHost("localhost");
        //request.setServerPort(8080);

        URL url = new URL(oAuth.signUrl(request.getRequestURL().toString()));
        Map<String, String> params = splitQuery(url);

        request.addHeader("Authorization", "OAuth "+
                "oauth_nonce=\""+params.get("oauth_nonce")+"\","+
                "oauth_timestamp=\""+params.get("oauth_timestamp")+"\","+
                "oauth_signature=\""+params.get("oauth_signature")+"\","+
                "oauth_consumer_key=\"rayproduct-11365\","+
                "oauth_version=\"1.0\","+
                "oauth_signature_method=\"HMAC-SHA1\"");
        oAuth.validate(request);
    }


    @Test(expected = UnauthorizedException.class)
    public void shouldValidateFailWhenNoAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/AppDirect/rest/api/events/dummyChange");
        request.setServerName("www.appdirect.com");
        request.setRemoteHost("www.appdirect.com");

        oAuth.validate(request);
    }

    /*

    GET /rest/api/events/dummyChange HTTP/1.1
Host: www.appdirect.com
Content-Type: application/xml
Authorization: OAuth realm="",
oauth_nonce="72250409",
oauth_timestamp="1294966759",
oauth_consumer_key="Dummy",
oauth_signature_method="HMAC-SHA1",
oauth_version="1.0",
oauth_signature="IBlWhOm3PuDwaSdxE/Qu4RKPtVE="
*/


    /*

    Authorization: OAuth realm="",
    oauth_nonce="4015234726731445260",
    oauth_timestamp="1407076250",
    oauth_signature="XRmEZBv6xYxjgYlfVoRgZyCBIEM%3D",
    oauth_consumer_key="rayproduct-11365",
    oauth_version="1.0",
    oauth_signature_method="HMAC-SHA1"


    */
}