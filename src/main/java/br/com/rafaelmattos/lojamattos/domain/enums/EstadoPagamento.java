package br.com.rafaelmattos.lojamattos.domain.enums;

//Implementação do Enum (somente o get, sem set)
public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	private EstadoPagamento(int cod, String descricao) {
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
	public static EstadoPagamento toEnum(Integer cod) {
		// o inteiro nulo corresponde ao estadoPagamento nulo tb
		if (cod == null) {
			return null;
		}
		// todo objeto x nos valores possiveis no estadoPagamento
		for (EstadoPagamento x : EstadoPagamento.values()) {
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