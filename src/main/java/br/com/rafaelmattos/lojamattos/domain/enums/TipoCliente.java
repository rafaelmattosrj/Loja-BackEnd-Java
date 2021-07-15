package br.com.rafaelmattos.lojamattos.domain.enums;

//Implementação do Enum (somente o get, sem set)
public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	// static -> possivel de ser executada mesmo sem instanciar objetos
	// converte para enum passando cod como parametro
	public static TipoCliente toEnum(Integer cod) {
		// o inteiro nulo corresponde ao tipoCliente nulo tb
		if (cod == null) {
			return null;
		}
		// todo objeto x nos valores possiveis no tipoCliente
		for (TipoCliente x : TipoCliente.values()) {
			// se o codigo que veio como argumento for igual ao x.getcod o codigo que estou
			// procurando,
			// vou retornar x
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		// exceção
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}