package com.everis.springboot.app.controllers;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.time.ZoneId;

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
  public Flux<Person> findAll() {
    return service.findAll();
  }
  
  @ApiOperation(value = "Buscar persona por Id", 
      notes = "Retorna todo sobre la persona encontrada")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
  @GetMapping("/{idPerson}")
  public Mono<Person> findById(@PathVariable final String idPerson) {
    return service.findById(idPerson);
  }

  @ApiOperation(value = "Buscar persona por documento ",
      notes = "Retorna todo sobre la persona encontrada")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
  @GetMapping("/document/{document}")
  //Metodo usado para buscar a una persona por documento
  public Mono<Person> findByDocument(@PathVariable final String document) {
    return service.findByNumberDocument(document);
  }

  @ApiOperation(value = "Buscar por nombre",
      notes = "Retorna las personas con el mismo nombre")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
    })
  @GetMapping("/name/{name}")
  public Flux<Person> findByName(@PathVariable final String name) {
    return service.findAll().filter(p -> p.getFullName().contains(name));
  }

  @ApiOperation(value = "Crear nuevo registro", notes = "Returns all infos")
  @ApiResponses({
            @ApiResponse(code = 201, message = "Creado correctamente")
    })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Person> create(@RequestBody final Person person) {
    return service.savePerson(person);
  }

  /**

   * Esto sirve para actualizar los datos de una persona realizando una busqueda
   * por Id de la persona.

   */
  @ApiOperation(value = "Actualizar datos de una persona", 
      notes = "Retorna todo de la persona actualizada")
  @ApiResponses({
        @ApiResponse(code = 201, message = "Actualizacion correcta")
  })
  @PutMapping("/{idPerson}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Person> update(@RequestBody final Person person,
      @PathVariable final String idPerson) {
    return service.findById(idPerson).map(p -> {
      p.setFullName(person.getFullName());
      p.setGender(person.getGender());
      p.setDateOfBirth(person.getDateOfBirth());
      p.setNumberDocument(person.getNumberDocument());
      p.setTypeDocument(person.getTypeDocument());
      p.setParentOne(person.getParentOne());
      p.setParentTwo(person.getParentTwo());
      p.setSpouse(person.getSpouse());
      return p;
    }).flatMap(p -> service.savePerson(p));
  }

  /**

   * Esto sirve para eliminar el registro de una persona.

   */
  @ApiOperation(value = "Eliminar una persona por Id", 
      notes = "Retorna vacío")
  @ApiResponses({
        @ApiResponse(code = 204, message = "Eliminado correctamente")
  })
  @DeleteMapping("/{idPerson}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(@PathVariable final String idPerson) {
    return service.findById(idPerson).flatMap(p -> {
      if (p.getId() == null) {
        return Mono.error(new InterruptedException("No existe el producto a eliminar"));
      }
      return Mono.just(p);
    }).flatMap(service::delete);
  }

  /**

   * Para agregar un nuevo familiar. Se guarda el id de la persona de quien
   * es el familiar.

   */
  @ApiOperation(value = "Agregar familiar",
      notes = "Retorna el familiar registrado")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Registro guardado correctamente")
    })
  @PutMapping("/addRelative/{idPerson}/{nameMember}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Person> addRelative(@PathVariable final String idPerson,
      @PathVariable final String nameMember,@RequestBody Person relative) {

    relative.setRelation(nameMember);
    relative.setIdRelative(idPerson);
    return service.savePerson(relative).flatMap(f ->
        service.findById(idPerson).flatMap(p -> {
          if ("parentOne".equals(nameMember)) { 
            p.setParentOne(f.getId());
          }
          if ("parentTwo".equals(nameMember)) {
            p.setParentTwo(f.getId());
          }
          if ("spouse".equals(nameMember)) {
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
  @GetMapping("/findFamily/{idPerson}")
  public Flux<Person> findFamily(@PathVariable final String idPerson) {
    return service.findByIdRelative(idPerson);
  }
  
  /**

   * Sirve para encontrar a las personas que tengan fecha de nacimiento
   * en el rango especificado.

   */
  @ApiOperation(value = "Buscar persona por rango de fechas yyyy-MM-dd", 
      notes = "Retorna la informacion de todos las personas encontradas")
  @ApiResponses({
        @ApiResponse(code = 200, message = "Encuentra por lo menos a una persona")
  })
  @GetMapping("/dateRange/{fecha1}/{fecha2}")
  public Flux<Person> findByDateRange(@PathVariable final String fecha1,
      @PathVariable final String fecha2) {
    final LocalDate fecha1LD = LocalDate.parse(fecha1).minusDays(2);
    final LocalDate fecha2LD = LocalDate.parse(fecha2).plusDays(1);
    return service.findAll()
      .filter(p -> p.getDateOfBirth()
        .toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate().isAfter(fecha1LD))
      .filter(p -> p.getDateOfBirth()
        .toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate().isBefore(fecha2LD));
  }

}
