package com.everis.springboot.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.springboot.app.models.documents.Person;



public interface PersonDao extends ReactiveMongoRepository<Person, String>{

}
