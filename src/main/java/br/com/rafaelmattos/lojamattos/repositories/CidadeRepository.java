package br.com.rafaelmattos.lojamattos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rafaelmattos.lojamattos.domain.Cidade;

@Repository
//Camada de acesso a dados
//herdar a classe por JpaRepository, atributo identificador
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
