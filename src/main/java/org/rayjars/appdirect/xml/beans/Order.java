package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="order")
public class Order {
	
	@XmlElement
	private String editionCode;

	@XmlElement(name="item")
	private List<Item> items;

	@XmlElement
	private String pricingDuration;


	public String getEditionCode() {
		return editionCode;
	}


	public Order setEditionCode(String editionCode) {
		this.editionCode = editionCode;
        return this;
	}


	public List<Item> getItems() {
		return items;
	}


	public Order setItems(List<Item> items) {
		this.items = items;
        return this;
	}


	public String getPricingDuration() {
		return pricingDuration;
	}


	public Order setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
        return this;
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order)) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			final Order order = (Order) obj;
			if(this.editionCode !=null && order.editionCode !=null){
				if (!(this.editionCode.equals(order.editionCode))) {
					return false;
				}
			}
			if (!(this.items.equals(order.items))) {
				return false;
			}
			if(this.pricingDuration !=null && order.pricingDuration !=null){	
				if (!(this.pricingDuration.equals(order.pricingDuration))) {
					return false;
				}
			}else
			{
				return true;
			}

			return true;
		}
	}
	
	

}
