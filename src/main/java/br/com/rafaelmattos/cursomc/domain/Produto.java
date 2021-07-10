package br.com.rafaelmattos.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;

	//Do outro lado da associação, já foram buscado os objetos, então agora não busque mais.
	// Vai omitir a lista de categorias, para cada produto.
	@JsonIgnore
	//Qdo tiver duas listas de cada lado(colocar em um dos dois lados).
	@ManyToMany
	//Quem vai ser a tabela q vai fazer muitos para muitos no banco relacional.
	@JoinTable(name = "PRODUTO_CATEGORIA", 
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	
	//Um produto tem uma ou mais categorias.
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	//Set para garantir q nao vai ter item repetido no msm pedido
	private Set<ItemPedido> itens = new HashSet<>();
	
	//Construtor vazio -> Instancio o objeto sem jogar nada para os atributos.
	public Produto() {
	}

	//Construtores -> Povoar os dados acima com o construtor.
	public Produto(Integer id, String nome, Double preco) { 
//Não coloca o que for lista. A categoria já foi iniciada, não entra no construtor.
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	@JsonIgnore
	//o produto conhece os pedidos dele
	//necessario criar um get pedidos, varrendo os itens de pedido, montando uma lista de pedidos associados a esse item.
	public List<Pedido> getPedido() {
		List<Pedido> lista = new ArrayList<>();
		//Percorrer todos os ItemPedido na lista de itens. Para cada pedido q existir na lista de itens...
		for (ItemPedido x : itens) {
			//...vai adicionar o pedido associado a ele na lista
			lista.add(x.getPedido());
		}
		return lista;
	}
	
	//Getters e setters -> Metodos de acesso para os atributos
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	//HashCode (id) -> gera um codigo numerico para cada objeto.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	//Equals -> Faz a comparações entre os objetos.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
