package org.rayjars.appdirect.xml;

import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.beans.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class XmlReader {

    public <T> T read(String url, Class<T> clazz) throws UnknownErrorException {
        try {
            return read(new URL(url).openStream(), clazz);
        } catch (IOException e) {
            throw new UnknownErrorException(e.getMessage());
        }
    }

    public <T> T read(InputStream content, Class<T> clazz) throws UnknownErrorException {
        try {
            JAXBContext jc = JAXBContext.newInstance(Event.class);
            return (T) jc.createUnmarshaller().unmarshal(content);
        } catch (JAXBException e) {
            throw new UnknownErrorException(e.getMessage());
        }

    }


}