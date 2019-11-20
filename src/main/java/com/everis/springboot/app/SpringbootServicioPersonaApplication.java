package com.everis.springboot.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringbootServicioPersonaApplication implements CommandLineRunner{

	@Autowired
	private PersonService service;
	
	private static final Logger log = LoggerFactory.getLogger(SpringbootServicioPersonaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioPersonaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Codigo para registrar personas en data dura.
		 *
		 
		  Flux.just(new Person(true, "Juan Carlos Rime Garay", "Masculino", new Date(), "DNI", "76563356"),
				new Person(true, "Anderson Miguel Flores del Huerto", "Masculino",new Date() , "DNI", "76563312"),
				new Person(true, "Jhonatan Maicelo Rojas Falcon", "Masculino", new Date(), "DNI", "98563356"),
				new Person(true, "Kleyber Mauricio Pinedo Varela", "Masculino", new Date(), "DNI", "76183356"),
				new Person(true, "Maria Asto Javier", "Femenino", new Date(), "DNI", "76563311"))
		.flatMap(service::savePerson)
		.subscribe(p->log.info("Se registro una persona"));
		 */
	}

}
