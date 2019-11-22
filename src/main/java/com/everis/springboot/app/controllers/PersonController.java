package com.everis.springboot.app.controllers;



import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/person")
@Api(value = "persona", description = "API para persona", produces = "application/json")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	
	@ApiOperation(value = "Listar todas las personas", 
			notes = "Retorna todo sobre todas las personas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
	@GetMapping
	public Flux<Person> findAll(){
		return service.findAll();
	}
	
	
	@ApiOperation(value = "Buscar persona por Id", 
			notes = "Retorna todo sobre la persona encontrada")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
	@GetMapping("/{id}")
	public Mono<Person> findById(@PathVariable String id){
		return service.findById(id);
	}
	
	
	@ApiOperation(value = "Buscar persona por documento ",
			notes = "Retorna todo sobre la persona encontrada")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
	@GetMapping("/document/{document}")
	public Mono<Person> findByDocument(@PathVariable String document){
		return service.findByNumberDocument(document);
	}
	
	
	@ApiOperation(value = "Buscar por nombre",
			notes = "Retorna las personas con el mismo nombre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
	@GetMapping("/name/{name}")
	public Flux<Person> findByName(@PathVariable String name){
		return service.findAll().filter(p->p.getFullName().contains(name));
	}
	
	
	@ApiOperation(value = "Crear nuevo registro", notes = "Returns all infos")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado correctamente")
    })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Person> create(@RequestBody Person person){
		return service.savePerson(person);
	}
	
	
	@ApiOperation(value = "Actualizar datos de una persona", 
			notes = "Retorna todo de la persona actualizada")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Actualizacion correcta")
    })
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
	
	
	@ApiOperation(value = "Eliminar una persona por Id", 
			notes = "Retorna vacío")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Eliminado correctamente")
    })
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
	
	
	@ApiOperation(value = "Agregar familiar",
			notes = "Retorna el familiar registrado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Registro guardado correctamente")
    })
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
	
	
	@ApiOperation(value = "Buscar familia de una persona",
			notes = "Retorna la informacion de todos los familiares")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a un familiar")
    })
	@GetMapping("/findFamily/{id}")
	public Flux<Person> findFamily(@PathVariable String id){
		return service.findByIdRelative(id);
	}
	
	@ApiOperation(value = "Buscar persona por rango de fechas yyyy-MM-dd", 
			notes = "Retorna la informacion de todos las personas encontradas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
	@GetMapping("/dateRange/{fecha1}/{fecha2}")
	public Flux<Person> findByDateRange(@PathVariable String fecha1,@PathVariable String fecha2){
		LocalDate fecha1LD = LocalDate.parse(fecha1).minusDays(2);
		LocalDate fecha2LD = LocalDate.parse(fecha2).plusDays(1);
		return service.findAll()
				.filter(p->p.getDateOfBirth()
						.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate().isAfter(fecha1LD))
				.filter(p->p.getDateOfBirth()
						.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate().isBefore(fecha2LD));
				
	}

}
