package br.com.rafaelmattos.lojamattos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.rafaelmattos.lojamattos.services.DBService;
import br.com.rafaelmattos.lojamattos.services.EmailService;
import br.com.rafaelmattos.lojamattos.services.MockEmailService;

//Configuração de teste
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		//instanciar a classe de serviços para teste
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
