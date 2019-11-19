package com.everis.springboot.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.springboot.app.models.dao.PersonDao;
import com.everis.springboot.app.models.documents.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	private PersonDao dao;

	@Override
	public Flux<Person> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Person> findAllById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Void> delete(Person persona) {
		return dao.delete(persona);
	}

	@Override
	public Mono<Person> savePerson(Person persona) {
		return dao.save(persona);
	}

}
