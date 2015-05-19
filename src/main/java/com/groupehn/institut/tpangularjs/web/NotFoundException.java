package com.groupehn.institut.tpangularjs.web;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6065185467235819340L;

	private final Class<?> type;

	private final Long id;

	public NotFoundException(final Class<?> type, final Long id) {
		super(type + " not found for id " + id);
		this.type = type;
		this.id = id;
	}

	public Class<?> getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

}
