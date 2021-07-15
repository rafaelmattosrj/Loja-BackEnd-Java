package br.com.rafaelmattos.lojamattos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rafaelmattos.lojamattos.domain.Endereco;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
