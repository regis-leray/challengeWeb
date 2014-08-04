package org.rayjars.appdirect.xml.beans;

import javax.xml.bind.annotation.*;



@XmlType(propOrder = { "quantity", "unit" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Item {
	@XmlElement
	private int quantity;

	@XmlElement
	private String unit;

	public int getQuantity() {
		return quantity;
	}

	public Item setQuantity(int quantity) {
		this.quantity = quantity;
        return this;
	}

	public String getUnit() {
		return unit;
	}

	public Item setUnit(String unit) {
		this.unit = unit;
        return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item)) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			final Item entry =  (Item) obj;
			if( !(this.quantity == entry.quantity)){
				return false;
			}
			if( !(this.unit.equals(entry.unit))){
				return false;
			}
			return true;			
		}
	}
	
	
}
