package br.com.rafaelmattos.lojamattos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Cliente;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//Ela nao precisa ser envolvida como uma transação de dados 
	@Transactional(readOnly = true)
	//Consulta JPQL no banco de dados
	Cliente findByEmail(String email);
	
}
