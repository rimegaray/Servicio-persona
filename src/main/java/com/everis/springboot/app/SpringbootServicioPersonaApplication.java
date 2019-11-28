package com.everis.springboot.app;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioPersonaApplication implements CommandLineRunner {

  @Autowired
private PersonService service;

  @Autowired
private ReactiveMongoTemplate mongoTemplate;

  private static final Logger log = LoggerFactory
      .getLogger(SpringbootServicioPersonaApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringbootServicioPersonaApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
   

  }
  
  /**

   * Esto sirve para borrar los registros de la base de datos y volver
   * a crear 5 datos de prueba.

   */
  
  public void reiniciarData() {
    mongoTemplate.dropCollection("persona").subscribe();
    Flux.just(new Person("Juan Carlos Rime Garay",
    "Masculino", new Date(), "DNI", "76563356"),
    new Person("Anderson Miguel Flores del Huerto",
    "Masculino",new Date(), "DNI", "76563312"),
    new Person("Jhonatan Maicelo Rojas Falcon",
    "Masculino", new Date(), "DNI", "98563356"),
    new Person("Kleyber Mauricio Pinedo Varela",
    "Masculino", new Date(), "DNI", "76183356"),
    new Person("Maria Asto Javier",
    "Femenino", new Date(), "DNI", "76563311"))
    .flatMap(service::savePerson)
      .subscribe(p -> log.info("Se registro una persona"));
  }

}
