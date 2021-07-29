package br.com.rafaelmattos.lojamattos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rafaelmattos.lojamattos.domain.Cliente;
import br.com.rafaelmattos.lojamattos.repositories.ClienteRepository;
import br.com.rafaelmattos.lojamattos.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repo;
	
	//busca do usuario do Spring Security pelo Username que no caso é o email
	@Override
	//vai receber os usuarios e vai retornar o UserDetails 
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
}