package com.everis.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;

import reactor.core.publisher.Flux;

@SpringBootTest
public class PersonServiceTest {
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	List<Person> lista = new ArrayList<>();
	  
	@Autowired
	PersonService service;
	
	@DisplayName("Test Spring @Autowired Integration")
    @Test
    void testGet() throws ParseException {
		lista.add(new Person(true, "Juan Carlos Rime Garay", "Masculino",
		        format.parse("1995-08-03"), "DNI", "76563356"));
		  lista.add(new Person(true, "Juan Carlos Rime Garay", "Masculino",
			  format.parse("1995-08-03"), "DNI", "76563356"));
        assertEquals(Flux.just(lista), service.findAll());
    }

}
