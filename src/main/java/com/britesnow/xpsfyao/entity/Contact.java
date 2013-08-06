package com.britesnow.xpsfyao.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
@javax.persistence.SequenceGenerator(name = "SEQ_STORE", allocationSize = 1, sequenceName = "contact_id_seq")
public class Contact extends BaseEntity{
	
	private String name;
	
	private String label;

	private String contact_id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getContact_id() {
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	
}
