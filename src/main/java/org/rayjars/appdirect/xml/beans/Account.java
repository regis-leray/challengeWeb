package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.*;

@XmlType(propOrder = { "accountIdentifier", "status" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Account {

    public Account(){

    }

    public Account(String accountIdentifier){
        this.accountIdentifier = accountIdentifier;
    }


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	@XmlElement
	private String accountIdentifier;

	@XmlElement
	private String status;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Account)) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			final Account entry = (Account) obj;
			if (!(this.accountIdentifier.equals(entry.accountIdentifier))) {
				return false;
			}

			return true;
		}
	}

}
