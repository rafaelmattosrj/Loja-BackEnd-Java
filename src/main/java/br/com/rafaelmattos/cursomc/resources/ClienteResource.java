package br.com.rafaelmattos.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.rafaelmattos.cursomc.domain.Cliente;
import br.com.rafaelmattos.cursomc.dto.ClienteDTO;
import br.com.rafaelmattos.cursomc.dto.ClienteNewDTO;
import br.com.rafaelmattos.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired // instanciar o serviço.
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// ResponseEntity -> Encapsula varias informações de uma resposta de HTTP para
	// um serviço REST. Cliente retorna todas as informações. O DTO limita as informações.
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
			Cliente obj = service.find(id);
			return ResponseEntity.ok().body(obj);
	}
	
	//Insere categoria //2
	@RequestMapping(method = RequestMethod.POST)
	//@Valid para validação //@Request Body, ou corpo da requisição, é onde geralmente enviamos dados que queremos gravar no servidor
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//Atualiza categoria //3
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	//Remove categoria //4
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Buscar uma lista de Clientes //5
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
			//converter Cliente em ClienteDto
			List<Cliente> list = service.findAll();
			List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDto);
	}
	
	//Clientes/page?page=01&linesPerPage=20...
	//Retorna todas Clientes com paginação //6
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			//@RequestParam -> parametro opcional
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}

