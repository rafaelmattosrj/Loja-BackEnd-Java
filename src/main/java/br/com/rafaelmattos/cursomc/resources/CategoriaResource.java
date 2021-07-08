package br.com.rafaelmattos.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelmattos.cursomc.domain.Categoria;
import br.com.rafaelmattos.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired // instanciar o serviço.
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// ResponseEntity -> Encapsula varias informações de uma resposta de HTTP para
	// um serviço REST.
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}
