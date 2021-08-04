package br.com.rafaelmattos.lojamattos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Cliente;
import br.com.rafaelmattos.lojamattos.domain.Pedido;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	//reduzir "lock"
	@Transactional(readOnly = true)
	//buscar pedidos por cliente, fazendo paginação
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
