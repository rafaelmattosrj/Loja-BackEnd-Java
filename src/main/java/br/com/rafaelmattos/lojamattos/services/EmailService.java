package br.com.rafaelmattos.lojamattos.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.rafaelmattos.lojamattos.domain.Pedido;

//Checklist:
// Adicionar a dependência no POM.XML
// Remetente e destinatário default no application.properties
// Criar a interface EmailService (padrão Strategy)
// Criar a classe abstrata AbstractEmailService
//o Criar método prepareSimpleMailMessageFromPedido
//o Sobrescrever o método sendOrderConfirmationEmail (padrão Template Method)
// Implementar o MockEmailService
// Em TestConfig, criar um método @Bean EmailService que retorna uma instância de MockEmailService
public interface EmailService {

	//versão texto plano
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
//
	//versão html
	void sendOrderConfirmationHtmlEmail(Pedido obj);	
	void sendHtmlEmail(MimeMessage msg);
}
