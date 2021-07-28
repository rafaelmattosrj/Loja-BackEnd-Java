package br.com.rafaelmattos.lojamattos.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.rafaelmattos.lojamattos.domain.Pedido;


//implementar um metodo baseado em metodos abstratos, que depois vao ser implementados pela interface
public abstract class AbstractEmailService implements EmailService {

	//@Value puxa a variavel
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	//protected -> esse metodo pode ser por subclasses, mas nao pelos usuarios
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//para qm vai ser essa mensagem?
		sm.setTo(obj.getCliente().getEmail());
		//qm vai ser o remetente 
		sm.setFrom(sender);
		//qual o assusnto?
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		//data do pedido
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//corpo do email
		sm.setText(obj.toString());
		return sm;
	}

}
