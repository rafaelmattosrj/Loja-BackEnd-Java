package br.com.rafaelmattos.lojamattos.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rafaelmattos.lojamattos.domain.Pedido;
import br.com.rafaelmattos.lojamattos.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired // instanciar o serviço.
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// ResponseEntity -> Encapsula varias informações de uma resposta de HTTP para
	// um serviço REST.
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
			Pedido obj = service.find(id);
			return ResponseEntity.ok().body(obj);
	}
	
	//Insere categoria //2
	@RequestMapping(method = RequestMethod.POST)
	//@Valid para validação //@Request Body, ou corpo da requisição, é onde geralmente enviamos dados que queremos gravar no servidor
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
