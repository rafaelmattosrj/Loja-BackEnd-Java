package br.com.rafaelmattos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelmattos.cursomc.domain.Categoria;
import br.com.rafaelmattos.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
			
		@Autowired 	//instanciar o repositorio
		private CategoriaRepository repo;
		
		public Categoria buscar(Integer id) {
			Optional<Categoria> obj = repo.findById(id);
			return obj.orElse(null);
			}
}
