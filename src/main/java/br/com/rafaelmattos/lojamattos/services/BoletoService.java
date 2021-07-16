package br.com.rafaelmattos.lojamattos.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.rafaelmattos.lojamattos.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	//criar um metodo para data de vencimento
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		//pegar calendario
		Calendar cal = Calendar.getInstance();
		//colocar da data do instante
		cal.setTime(instanteDoPedido);
		//acrescentar 7 dias
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
