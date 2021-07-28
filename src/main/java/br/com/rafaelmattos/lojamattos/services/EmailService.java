package br.com.rafaelmattos.lojamattos.services;

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

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

}
