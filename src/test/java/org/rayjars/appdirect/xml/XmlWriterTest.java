package org.rayjars.appdirect.xml;

import org.junit.Test;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.beans.Result;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.*;

public class XmlWriterTest {

    XmlWriter writer = new XmlWriter();

    @Test
    public void shouldCreateXmlErrorMessage() throws UnknownErrorException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writer.write(createError(), stream);

        String content = new String(stream.toByteArray());

        assertThat(content).contains("<success>false</success>");
        assertThat(content).contains("<errorCode>UNKNOW</errorCode>");
        assertThat(content).contains("<message>error message</message>");
    }

    private Result createError() {
        Result error = new Result();
        error.setSuccess(false);
        error.setErrorCode("UNKNOW");
        error.setMessage("error message");
        return error;
    }

}