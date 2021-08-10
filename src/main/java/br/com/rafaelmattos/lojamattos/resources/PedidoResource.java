package br.com.rafaelmattos.lojamattos.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rafaelmattos.lojamattos.domain.Pedido;
import br.com.rafaelmattos.lojamattos.services.PedidoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired // instanciar o serviço.
	private PedidoService service;

	@ApiOperation(value="Busca por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// ResponseEntity -> Encapsula varias informações de uma resposta de HTTP para
	// um serviço REST.
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
			Pedido obj = service.find(id);
			return ResponseEntity.ok().body(obj);
	}
	
	//Insere categoria //2
	@ApiOperation(value="Insere pedido")
	@RequestMapping(method = RequestMethod.POST)
	//@Valid para validação //@Request Body, ou corpo da requisição, é onde geralmente enviamos dados que queremos gravar no servidor
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//categorias/page?page=01&linesPerPage=20...
	//1. [OUT] O sistema informa os nomes de todas categorias ordenadamente.
	@ApiOperation(value="Retorna todos pedidos com paginação")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
	
}
