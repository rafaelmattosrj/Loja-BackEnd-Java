package br.com.rafaelmattos.lojamattos.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.rafaelmattos.lojamattos.domain.Pedido;

//implementar um metodo baseado em metodos abstratos, que depois vao ser implementados pela interface
public abstract class AbstractEmailService implements EmailService {

	// @Value puxa a variavel
	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	// protected -> esse metodo pode ser por subclasses, mas nao pelos usuarios
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		// para qm vai ser essa mensagem?
		sm.setTo(obj.getCliente().getEmail());
		// qm vai ser o remetente
		sm.setFrom(sender);
		// qual o assusnto?
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		// data do pedido
		sm.setSentDate(new Date(System.currentTimeMillis()));
		// corpo do email
		sm.setText(obj.toString());
		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}

	//se der manda o html, se nao der, manda o texto plano
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		//para qm vai ser esse email?
		mmh.setTo(obj.getCliente().getEmail());
		//qm vai ser o remetente do email?
		mmh.setFrom(sender);
		//assunto do email?
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		//instante do email
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		//qm vai ser o corpo do email? //true é igual html
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;

	}

}
