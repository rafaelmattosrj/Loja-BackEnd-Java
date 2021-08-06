package br.com.rafaelmattos.lojamattos.resources.exception;

import java.util.ArrayList;
import java.util.List;

//tem is dados do standard error
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	//lista de mensagens de erro
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}
