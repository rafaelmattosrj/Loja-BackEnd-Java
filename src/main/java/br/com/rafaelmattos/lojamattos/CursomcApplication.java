package br.com.rafaelmattos.lojamattos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.rafaelmattos.lojamattos.services.S3Service;

@SpringBootApplication
//CommandLineRunner -> Implementar um metodo auxiliar executar uma ação qdo a aplicação iniciar.
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	//metodo vazio para caso necessita implementar alguma coisa
	@Override
	public void run(String... args) throws Exception {		
		s3Service.uploadFile("C:\\temp\\eu.jpg");
	}

}
