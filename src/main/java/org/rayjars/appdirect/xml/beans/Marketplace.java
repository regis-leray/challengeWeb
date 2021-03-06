package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.*;



@XmlType(propOrder = { "baseUrl", "partner" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Marketplace {

	@XmlElement
	private String baseUrl;

	@XmlElement
	private String partner;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Marketplace)) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			final Marketplace marketplace = (Marketplace) obj;
			if ((!this.baseUrl.equals(marketplace.getBaseUrl()))) {
				return false;
			}
			if ((!this.partner.equals(marketplace.getPartner()))) {
				return false;
			}
			return true;
		}
	}

}
