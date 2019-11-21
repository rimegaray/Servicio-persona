package com.everis.springboot.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.springboot.app.models.documents.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface PersonDao extends ReactiveMongoRepository<Person, String>{
	Flux<Person> findByFullNameContains(String fullName);
	Mono<Person> findByNumberDocument(String document);
	Flux<Person> findByIdRelative(String id);
}
