package com.groupehn.institut.tpangularjs.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupehn.institut.tpangularjs.model.Contact;
import com.groupehn.institut.tpangularjs.model.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactForm {

	private String firstName;

	private String lastName;

	private Set<FieldForm> fields;

	public ContactForm() {
		super();
		fields = new HashSet<FieldForm>();
	}

	public void update(final Contact contact, final EntityManager em) {
		contact.rename(firstName, lastName);

		/* Identification des nouveaux champs et des champs existants. */
		final Set<FieldForm> newFields = new HashSet<FieldForm>();
		final Map<Long, FieldForm> existingFields = new HashMap<Long, FieldForm>();
		for (final FieldForm field : fields) {
			if (field.getId() == null) {
				newFields.add(field);
			} else {
				existingFields.put(field.getId(), field);
			}
		}

		/* Mise à jour des champs modifiés/supprimés. */
		final Iterator<Field> fieldsIter = contact.getFields().iterator();
		while (fieldsIter.hasNext()) {
			final Field field = fieldsIter.next();
			final FieldForm form = existingFields.get(field.getId());
			if (form == null) {
				em.remove(field);
				fieldsIter.remove();
			} else {
				form.update(field);
			}
		}

		createNewFields(contact, newFields, em);
	}

	public Contact createContact(final EntityManager em) {
		final Contact contact = new Contact(firstName, lastName);
		em.persist(contact);
		createNewFields(contact, fields, em); // Nouveau contact, donc tous les champs sont nouveaux.
		return contact;
	}

	private void createNewFields(final Contact contact, final Set<FieldForm> newFields, final EntityManager em) {
		for (final FieldForm form : newFields) {
			final Field field = form.createField(contact);
			em.persist(field);
			contact.getFields().add(field);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<FieldForm> getFields() {
		return fields;
	}

	public void setFields(Set<FieldForm> fields) {
		this.fields = fields;
	}

}
