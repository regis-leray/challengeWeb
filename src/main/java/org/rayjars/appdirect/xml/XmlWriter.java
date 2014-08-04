package org.rayjars.appdirect.xml;

import org.rayjars.appdirect.exceptions.UnknownErrorException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;


public class XmlWriter {

    public <T> void write(T instance, OutputStream stream) throws UnknownErrorException {

        try {
            JAXBContext jc = JAXBContext.newInstance(instance.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(instance, stream);
        } catch (JAXBException e) {
            throw new UnknownErrorException(e.getMessage());
        }


    }
}
