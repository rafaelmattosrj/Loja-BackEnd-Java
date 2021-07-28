package br.com.rafaelmattos.lojamattos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelmattos.lojamattos.domain.Cidade;
import br.com.rafaelmattos.lojamattos.domain.Cliente;
import br.com.rafaelmattos.lojamattos.domain.Endereco;
import br.com.rafaelmattos.lojamattos.domain.enums.TipoCliente;
import br.com.rafaelmattos.lojamattos.dto.ClienteDTO;
import br.com.rafaelmattos.lojamattos.dto.ClienteNewDTO;
import br.com.rafaelmattos.lojamattos.repositories.ClienteRepository;
import br.com.rafaelmattos.lojamattos.repositories.EnderecoRepository;
import br.com.rafaelmattos.lojamattos.services.exceptions.DataIntegrityException;
import br.com.rafaelmattos.lojamattos.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired // instanciar o repositorio
	private BCryptPasswordEncoder pe;
	
	@Autowired // instanciar o repositorio
	private ClienteRepository repo;
	
	@Autowired // instanciar o repositorio
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		//Um função que estância uma exceção (utilizou uma expressão lambda)
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	// Inserir //2
	//@Transactional salvar tanto o cliente qto o endereco na msm transação de dados
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	// Atualizar //3
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	//private pq é metodo auxiliar //3
	private void updateData(Cliente newObj, Cliente obj) {
		//possibilidades de atuliazar com o put.
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	// Deletar //4
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionadas");
		}
	}
	
	//Listar categoria //5
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	//Paginação //6
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//Metodo auxiliar que apartir de uma Cliente instancia o DTO
	// Construção
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
		
}
