package com.groupehn.institut.tpangularjs.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.groupehn.institut.tpangularjs.model.Contact;
import com.groupehn.institut.tpangularjs.model.Field;
import com.groupehn.institut.tpangularjs.web.ContactForm;
import com.groupehn.institut.tpangularjs.web.NotFoundException;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@PersistenceContext
	private EntityManager em;

	private CriteriaQuery<Contact> fetchAll;

	@PostConstruct
	private void postConstruct() {
		final CriteriaBuilder cb = emf.getCriteriaBuilder();
		fetchAll = cb.createQuery(Contact.class);
		final Root<Contact> root = fetchAll.from(Contact.class);
		fetchAll.select(root);
	}

	@RequestMapping("/list")
	@Transactional(readOnly = true)
	@JsonView(Contact.Summary.class)
	public List<Contact> list() {
		return em.createQuery(fetchAll).getResultList();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public Contact get(@PathVariable final Long id) {
		return loadContact(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void edit(@PathVariable final Long id, @RequestBody final ContactForm form) {
		final Contact contact = loadContact(id);
		form.update(contact, em);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void delete(@PathVariable final Long id) {
		final Contact contact = loadContact(id);
		for (final Field field : contact.getFields()) {
			em.remove(field);
		}
		contact.getFields().clear();
		em.remove(contact);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public Long create(@RequestBody final ContactForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		final Contact contact = form.createContact(em);
		em.flush();
		final Long id = contact.getId();
		response.setHeader("Location", request.getRequestURI().replace("new", id.toString()));
		return id;
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
		// Rien à faire, les annotations suffisent.
	}

	private Contact loadContact(final Long id) {
		final Contact contact = em.find(Contact.class, id);
		if (contact == null) {
			throw new NotFoundException(Contact.class, id);
		}
		contact.getFields().iterator(); // Pré-chargement des champs.
		return contact;
	}

}
