package br.com.rafaelmattos.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

//tem is dados do standard error
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	//lista de mensagens
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}
