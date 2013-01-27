package org.amidani.labs.om.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class Earning {
	@Id
	private Long id;
	@JsonProperty
	private String label;
	@JsonProperty
	private int amount;
	
	public Earning(){}
	
	public Earning(Long id, String label, int amount){
		this.id=id;
		this.label=label;
		this.amount=amount;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
