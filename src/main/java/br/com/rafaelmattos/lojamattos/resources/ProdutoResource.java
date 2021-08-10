package br.com.rafaelmattos.lojamattos.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelmattos.lojamattos.domain.Produto;
import br.com.rafaelmattos.lojamattos.dto.ProdutoDTO;
import br.com.rafaelmattos.lojamattos.resources.utils.URL;
import br.com.rafaelmattos.lojamattos.services.ProdutoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired // instanciar o serviço.
	private ProdutoService service;

	@ApiOperation(value="Busca por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// ResponseEntity -> Encapsula varias informações de uma resposta de HTTP para
	// um serviço REST.
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
			Produto obj = service.find(id);
			return ResponseEntity.ok().body(obj);
	}
	
	//Busca paginada
	//categorias/page?page=01&linesPerPage=20...
	//Retorna todas categorias com paginação //6
	@ApiOperation(value="Retorna todos produtos com paginação")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			//@RequestParam -> parametro opcional
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
}
