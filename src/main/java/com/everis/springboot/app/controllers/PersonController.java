package com.everis.springboot.app.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@GetMapping
	public Flux<Person> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Person> findById(@PathVariable String id){
		return service.findById(id);
	}
	
	@GetMapping("/document/{document}")
	public Mono<Person> findByDocument(@PathVariable String document){
		return service.findByNumberDocument(document);
	}
	
	@GetMapping("/name/{name}")
	public Flux<Person> findByName(@PathVariable String name){
		return service.findAll().filter(p->p.getFullName().contains(name));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Person> create(@RequestBody Person person){
		return service.savePerson(person);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Person> update(@RequestBody Person person, @PathVariable String id){
		 
		return service.findById(id).map(p->{
			p.setFullName(person.getFullName());
			p.setGender(person.getGender());
			p.setDateOfBirth(person.getDateOfBirth());
			p.setNumberDocument(person.getNumberDocument());
			p.setTypeDocument(person.getTypeDocument());
			p.setParentOne(person.getParentOne());
			p.setParentTwo(person.getParentTwo());
			p.setSpouse(person.getSpouse());
			p.setStudent(person.isStudent());
			return p;
		}).flatMap(p->service.savePerson(p));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable String id){
		return service.findById(id).flatMap(p -> {
			if (p.getId() == null) {
				return Mono.error(new InterruptedException("No existe el producto a eliminar"));
			}
			return Mono.just(p);
		}).flatMap(service::delete);
	}
	
	@PutMapping("/addRelative/{id}/{nameMember}")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Person> addRelative(@PathVariable String id,
			@PathVariable String nameMember,@RequestBody Person relative){
		
		/*
		 * Se guarda el familiar como nueva Persona y se actualiza a la persona por id
		 * String id: ID de la persona a la que se desea agregar el familiar.		
		 * String nameMember: el nombre de la relacion que tiene el nuevo familiar. 
		 * 	Person relative: El familiar nuevo para registrar
		 */
		 relative.setRelation(nameMember);
		 relative.setIdRelative(id);
		return service.savePerson(relative).flatMap(f->
				service.findById(id).flatMap(p->{
					if(nameMember.equals("parentOne")) {
						p.setParentOne(f.getId());
					}
					if(nameMember.equals("parentTwo")) {
						p.setParentTwo(f.getId());
					}
					if(nameMember.equals("spouse")) {
						p.setSpouse(f.getId());
					}
					return Mono.just(p);
				})
				
		).flatMap(service::savePerson);
	}
	
	@GetMapping("/findFamily/{id}")
	public Flux<Person> findFamily(@PathVariable String id){
		return service.findByIdRelative(id);
	}
	

}
