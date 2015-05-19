package com.groupehn.institut.tpangularjs.web;

import com.groupehn.institut.tpangularjs.model.Contact;
import com.groupehn.institut.tpangularjs.model.Field;

public class FieldForm {

	private Long id;

	private Integer order;

	private String type;

	private String value;

	public FieldForm() {
		super();
	}

	public void update(final Field field) {
		field.setOrder(order);
		field.setType(type);
		field.setValue(value);
	}

	public Field createField(final Contact contact) {
		return new Field(contact, order, type, value);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
