package br.com.rafaelmattos.lojamattos.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rafaelmattos.lojamattos.domain.enums.Perfil;
import br.com.rafaelmattos.lojamattos.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//Faz o banco de dados garantir q nao terá repetição nesse campo 
	@Column(unique = true)
	private String email;
	private String cpfOuCnpj;
	//armazenar como numero inteiro
	private Integer tipo;

	//para nao aparecer no bcrpt da senha
	@JsonIgnore
	private String senha;
	
	//É o lado que vem os objetos associados.
	//@JsonManagedReference
	// Cliente tem varios endereços, mapeamento reverso, atributo q mapeou na outra classe.
	//cascade = CascadeType.ALL -> toda operação que modificar um cliente, eu vou poder refletir isso em cascata nos endereços.
	// apaga o proximo automaticamente.
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	//Mapeamento dos telefones (ElementCollection)
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	//Set é um conjunto que não permite repetição
	private Set<String> telefones = new HashSet<>();
	
	//Garantir que ao buscar os clientes no BD, buscar os perfis tb
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	//Set é um conjunto que não permite repetição
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonIgnore
	//mapeado pelo cliente
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
		//todo mundo por padrão terá perfil de cliente
		addPerfil(Perfil.CLIENTE);
	}

	// facilitar a instanciação de objetos numa linha só 
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		// Não coloca o que for lista ou coleção.
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		//se o tipo for igual a nulo eu vou atribuir nulo para esse campo, caso acontrario, atribui o codigo
		this.tipo = (tipo==null) ? null : tipo.getCod(); //acrescentar .getCod()
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo); //alterar
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod(); //acrescentar .getCod()
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	//é do tipo enumerado
	public Set<Perfil> getPerfis() {
		//Lambda -> percorrer essa coleção convertendo todo mundo para o tipo enumerado perfil
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	//ir na coleção perfis para adicionar o codigo dele
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
