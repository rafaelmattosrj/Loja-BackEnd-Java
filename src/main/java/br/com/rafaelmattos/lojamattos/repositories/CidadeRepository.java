package br.com.rafaelmattos.lojamattos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Cidade;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	//Ela nao precisa ser envolvida como uma transação de dados 
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
	
}
