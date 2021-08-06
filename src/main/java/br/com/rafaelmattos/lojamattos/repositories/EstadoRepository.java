package br.com.rafaelmattos.lojamattos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Estado;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Transactional(readOnly = true)
	//Consulta JPQL no banco de dados
	public List<Estado> findAllByOrderByNome();
	
}
