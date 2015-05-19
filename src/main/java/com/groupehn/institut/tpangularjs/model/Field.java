package com.groupehn.institut.tpangularjs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CONTACT")
	@JsonIgnore
	private Contact contact;

	@Column(name = "ORDER_")
	private Integer order;

	@Column(name = "TYPE_")
	private String type;

	@Column(name = "VALUE_")
	private String value;

	protected Field() {
		super();
	}

	public Field(final Contact contact, final Integer order, final String type, final String value) {
		super();
		this.contact = contact;
		this.order = order;
		this.type = type;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public Contact getContact() {
		return contact;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
