package org.rayjars.appdirect;

import net.oauth.*;
import net.oauth.server.OAuthServlet;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.rayjars.appdirect.exceptions.UnauthorizedException;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;

public final class OAuth {

    final Logger logger = LoggerFactory.getLogger(OAuth.class);

    private String consumerSecret;

    private String consumerKey;


    public void validate(HttpServletRequest request) throws UnauthorizedException {

        try {
            logger.debug("Request Method {} - URL {}",request.getMethod(), request.getRequestURL().toString());
            logger.debug("Request consumerKey {} - consumerSecret {}",consumerKey, consumerSecret);

            OAuthMessage oauthMessage= OAuthServlet.getMessage(request, null);

            //OAuthServlet.getMessage(request, null);
            OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, consumerSecret, null);
            OAuthAccessor accessor = new OAuthAccessor(consumer);
            new SimpleOAuthValidator().validateMessage(oauthMessage, accessor);
        } catch (OAuthException | URISyntaxException | IOException  e) {
            logger.error("Failed to validate {}", e.getMessage());

            throw new UnauthorizedException(e.getMessage());
        }
    }

    public String signUrl(String urlString) throws UnknownErrorException {
        try {
            DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
            consumer.setSigningStrategy(new QueryStringSigningStrategy());
            return consumer.sign(urlString);
        }  catch (OAuthExpectationFailedException | OAuthCommunicationException | OAuthMessageSignerException e) {
            throw new UnknownErrorException(e.getMessage());
        }
    }

    @Value("${CONSUMER_SECRET}")
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }


    @Value("${CONSUMER_KEY}")
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }
}