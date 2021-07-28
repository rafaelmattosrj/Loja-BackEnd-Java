package br.com.rafaelmattos.lojamattos.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.ItemPedido;
import br.com.rafaelmattos.lojamattos.domain.PagamentoComBoleto;
import br.com.rafaelmattos.lojamattos.domain.Pedido;
import br.com.rafaelmattos.lojamattos.domain.enums.EstadoPagamento;
import br.com.rafaelmattos.lojamattos.repositories.ItemPedidoRepository;
import br.com.rafaelmattos.lojamattos.repositories.PagamentoRepository;
import br.com.rafaelmattos.lojamattos.repositories.PedidoRepository;
import br.com.rafaelmattos.lojamattos.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // instanciar o repositorio
	private PedidoRepository repo;
	
	@Autowired // instanciar o repositorio
	private BoletoService boletoService;
	
	@Autowired // instanciar o repositorio
	private PagamentoRepository pagamentoRepository;
	
	@Autowired // instanciar o repositorio
	private ProdutoService produtoService;
	
	@Autowired // instanciar o repositorio
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired // instanciar o repositorio
	private ClienteService clienteService;
	
	@Autowired // instanciar o repositorio
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		//Um função que estância uma exceção (utilizou uma expressão lambda)
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	//metodo para inserir um pedido
	@Transactional
	public Pedido insert(Pedido obj) {
		//setar o id para nulo, para garantir q é um novo pedido
		obj.setId(null);
		//criar nova data com instante atual
		obj.setInstante(new Date());
		//vai usar o id para buscar no banco de dados o cliente, setando o cliente associado ao obj.
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		//estado do pagamento qdo insere
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		//associação de mao dupla, o pagmaento tem q conhecer o pedido dele
		obj.getPagamento().setPedido(obj);
		//data de vencimento, uma semana depois da data do pedido
		//se o meu pagamento for do tipo pagamento com boleto
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			//vai preencher o pagamento com a data de vencimento
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		//salvar no banco de dados o pedido
		obj = repo.save(obj);
		//salvar no banco de dados o pagamento
		pagamentoRepository.save(obj.getPagamento());
		//pecorrer todos os itempedido associados ao obj.getitens
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			//setar o produto
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			//o preco tem q copiar do produto, vai ter q buscar no banco de dados
			ip.setPreco(ip.getProduto().getPreco());
			//associar item de pedido com obj
			ip.setPedido(obj);
		}
		//salvar itempedido
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
}
