package org.rayjars.appdirect.xml;

import org.junit.Test;
import org.rayjars.appdirect.xml.beans.Event;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


public class XmlReaderTest {

    XmlReader reader = new XmlReader();

    @Test
    public void shouldReadCancel() throws Exception {
        URL resource = this.getClass().getResource("/dummyCancel.xml");
        Event event = reader.read(resource.toString(), Event.class);

        assertThat(event.getPayload().getAccount().getAccountIdentifier()).isEqualTo("dummy-account");
    }

    @Test
    public void shouldReadOrder() throws Exception {
        URL resource = this.getClass().getResource("/dummyOrder.xml");
        Event event = reader.read(resource.toString(), Event.class);

        assertThat(event.getPayload().getOrder().getEditionCode()).isEqualTo("BASIC");
        assertThat(event.getPayload().getOrder().getPricingDuration()).isEqualTo("MONTHLY");
        assertThat(event.getPayload().getOrder().getItems()).extracting("unit", "quantity").contains(tuple("USER", 10), tuple("MEGABYTE", 15));

    }

    @Test
    public void shouldReadChange() throws Exception {
        URL resource = this.getClass().getResource("/dummyChange.xml");
        Event event = reader.read(resource.toString(), Event.class);

        assertThat(event.getPayload().getOrder().getEditionCode()).isEqualTo("PREMIUM");
        assertThat(event.getPayload().getOrder().getPricingDuration()).isEqualTo("MONTHLY");
        assertThat(event.getPayload().getOrder().getItems()).extracting("unit", "quantity").contains(tuple("USER", 20), tuple("MEGABYTE", 15));

    }


    @Test
    public void shouldReadUnassign() throws Exception {
        URL resource = this.getClass().getResource("/dummyUnassign.xml");
        Event event = reader.read(resource.toString(), Event.class);

        assertThat(event.getPayload().getAccount().getAccountIdentifier()).isEqualTo("3df6ce3c-a273-47e5-8304-e1770e5e9e11");
        assertThat(event.getPayload().getUser().getUuid()).isEqualTo("ec5d8eda-5cec-444d-9e30-125b6e4b67e2");
        assertThat(event.getPayload().getUser().getFirstName()).isEqualTo("DummyFirst");
        assertThat(event.getPayload().getUser().getLastName()).isEqualTo("DummyLast");
    }

    @Test
    public void shouldReadAssign() throws Exception {
        URL resource = this.getClass().getResource("/dummyAssign.xml");
        Event event = reader.read(resource.toString(), Event.class);

        assertThat(event.getPayload().getAccount().getAccountIdentifier()).isEqualTo("cf87c4de-e87e-4ec6-bf8c-46005d517f85");
        assertThat(event.getPayload().getUser().getUuid()).isEqualTo("ec5d8eda-5cec-444d-9e30-125b6e4b67e2");
        assertThat(event.getPayload().getUser().getFirstName()).isEqualTo("DummyFirst");
        assertThat(event.getPayload().getUser().getLastName()).isEqualTo("DummyLast");
    }



}
