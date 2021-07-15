package br.com.rafaelmattos.lojamattos.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// Mensagem de exceção.
	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	// Recebe a mensagem e a causa do que aconteceu antes.
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

//Está associado a CategoriaService
