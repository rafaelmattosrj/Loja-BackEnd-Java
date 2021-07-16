package br.com.rafaelmattos.lojamattos.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.rafaelmattos.lojamattos.domain.enums.EstadoPagamento;

//Extends subclasse do pagamento
//Subclasse não precisa de hashcode and equals
@Entity
//campo adicional para o JSON
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	//Subclasse não precisa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
	}

	//Por ser Subclasse, fazer adaptação no construtor
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	} 

	
	
}
