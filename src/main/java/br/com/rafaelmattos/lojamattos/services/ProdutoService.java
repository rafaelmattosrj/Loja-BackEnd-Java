package br.com.rafaelmattos.lojamattos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rafaelmattos.lojamattos.domain.Categoria;
import br.com.rafaelmattos.lojamattos.domain.Produto;
import br.com.rafaelmattos.lojamattos.repositories.CategoriaRepository;
import br.com.rafaelmattos.lojamattos.repositories.ProdutoRepository;
import br.com.rafaelmattos.lojamattos.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired // instanciar o repositorio
	private ProdutoRepository repo;
	
	@Autowired // instanciar o repositorio
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		//Um função que estância uma exceção (utilizou uma expressão lambda)
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	//É uma busca paginada
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		//O repository busca todas as categorias que tiverem correspondentes aos ids
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		//return repo.search(nome, categorias, pageRequest);
		
	}
	
}
