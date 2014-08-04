package org.rayjars.appdirect.web;

import org.rayjars.appdirect.SubscriptionRepository;
import org.rayjars.appdirect.OAuth;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.beans.Event;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

    protected XmlReader reader;

    protected OAuth oAuth;

    protected SubscriptionRepository accountDao;

    protected Event signAndfetch(String url) throws UnknownErrorException {
              String signUrl = oAuth.signUrl(url);
        return reader.read(signUrl, Event.class);
    }

    protected String getAccountIdentifier(Event event) {
        return event.getPayload().getAccount().getAccountIdentifier();
    }

    @Autowired
    public void setoAuth(OAuth oAuth) {
        this.oAuth = oAuth;
    }

    @Autowired
    public void setReader(XmlReader reader) {
        this.reader = reader;
    }


    @Autowired
    public void setAccountDao(SubscriptionRepository accountDao) {
        this.accountDao = accountDao;
    }


}
