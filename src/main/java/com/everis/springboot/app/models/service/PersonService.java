package com.everis.springboot.app.models.service;

import com.everis.springboot.app.models.documents.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface PersonService {

  public Flux<Person> findAll();

  public Mono<Person> findById(String id);

  public Mono<Void> delete(Person persona);

  public Mono<Person> savePerson(Person persona);

  public Flux<Person> findFullNameContains(String fullName);

  public Mono<Person> findByNumberDocument(String document);

  public Mono<Person> findParentOne(String id);

  public Mono<Person> findParentTwo(String id);

  public Flux<Person> findByIdRelative(String id);
	
}
