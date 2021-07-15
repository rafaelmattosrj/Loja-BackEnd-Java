package br.com.rafaelmattos.lojamattos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Categoria;
import br.com.rafaelmattos.lojamattos.domain.Produto;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	//Busca JPQL por ser consulta diferenciada
	//http://localhost:8080/produtos/?nome=or&categorias=1,4
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	@Transactional(readOnly = true) //É apenas uma consulta, não precisa fazer uma transação
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	

	
}
