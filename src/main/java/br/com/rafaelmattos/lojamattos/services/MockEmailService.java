package br.com.rafaelmattos.lojamattos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

//envio do email de mentira para testes
public class MockEmailService extends AbstractEmailService {

	//mostrar o email no logger do servidor. static para ter um so para todo mundo, nao criar outro.
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

}

