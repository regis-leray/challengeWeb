package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "notice")
public class Notice {

    @XmlElement
    private String type;

    public String getType() {
        return type;
    }

    public Notice setType(String type) {
        this.type = type;
        return this;
    }
}
