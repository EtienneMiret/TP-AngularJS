package com.groupehn.institut.tpangularjs.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Contact {

	public interface Summary {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Summary.class)
	private Long id;

	@JsonView(Summary.class)
	private String firstName;

	@JsonView(Summary.class)
	private String lastName;

	@OneToMany(mappedBy = "contact")
	private Set<Field> fields;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modification;

	protected Contact() {
		super();
	}

	public Contact(final String firstName, final String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fields = new HashSet<Field>();
		this.creation = new Date();
		this.modification = creation;
	}

	public void rename(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.modification = new Date();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public Date getCreation() {
		return creation;
	}

	public Date getModification() {
		return modification;
	}

}
