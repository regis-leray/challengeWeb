package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.*;

@XmlType(propOrder = { "email", "firstName", "lastName", "openId", "uuid"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="user")
public class User {

    public User(String uuid) {
        this.uuid = uuid;
    }

    public User() {
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @XmlElement
	private String email;

	@XmlElement
	private String firstName;
	
	@XmlElement
	private String lastName;
	
	@XmlElement
	private String openId;

    @XmlElement
    private String uuid;
}
