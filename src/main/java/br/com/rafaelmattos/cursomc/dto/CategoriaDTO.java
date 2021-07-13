package br.com.rafaelmattos.cursomc.dto;

import java.io.Serializable;

import br.com.rafaelmattos.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	//tirou a lista de produtos
	
	public CategoriaDTO() {
	}
	
	//Converter uma lista de Categorias em Lista DTO
	//Construtor que recebe um objeto correspondente das entidades de dominio.
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
