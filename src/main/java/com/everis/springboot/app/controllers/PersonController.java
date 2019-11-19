package com.everis.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/person")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping("/findAll")
	public Flux<Person> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/findById/{id}")
	public Mono<Person> findById(@PathVariable String id){
		return service.findAllById(id);
	}
	
	@GetMapping("/findByDocument/{document}")
	public Mono<Person> findByDocument(@PathVariable String document){
		return service.findAll().filter(p->p.getNumberDocument().equals(document)).next();
	}
	
	@GetMapping("/findByName/{name}")
	public Mono<Person> findByName(@PathVariable String name){
		return service.findAll().filter(p->p.getFullName().contains(name)).next();
	}
	
	
	
	

}
