package com.everis.springboot.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.everis.springboot.app.models.dao.PersonDao;
import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.service.PersonService;
import com.everis.springboot.app.models.service.PersonServiceImpl;

import reactor.core.publisher.Flux;

@SpringBootTest
class SpringbootServicioPersonaApplicationTests {

  @InjectMocks
  private PersonService service = new PersonServiceImpl();

  @Mock
  private PersonDao dao;
  
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
  List<Person> lista = new ArrayList<>();

  @BeforeEach
  void setMockOutput() throws ParseException {
    
	  /*
			   * lista.add(new Person(true, "Juan Carlos Rime Garay", "Masculino",
			   
		        format.parse("1995-08-03"), "DNI", "76563356"));
		  lista.add(new Person(true, "Juan Carlos Rime Garay", "Masculino",
			  format.parse("1995-08-03"), "DNI", "76563356"));
		  when(dao.findAll())
		  .thenReturn(Flux.fromIterable(lista));
		  }
		
		  @DisplayName("Test Mock helloService + helloRepository")
		  @Test
		  void testGet() throws ParseException {
			  assertEquals(Flux.fromIterable(lista), service.findAll());
	  */
  }
  
  
  

}
