package br.com.rafaelmattos.lojamattos.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.rafaelmattos.lojamattos.security.JWTAuthenticationFilter;
import br.com.rafaelmattos.lojamattos.security.JWTAuthorizationFilter;
import br.com.rafaelmattos.lojamattos.security.JWTUtil;

@Configuration
@EnableWebSecurity
//travar as liberações
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//Vetor de strings que estarma liberados
	private static final String[] PUBLIC_MARCHERS = { 
			"/h2-console/**"
	};
	
	//caminho para obter os dados
	private static final String[] PUBLIC_MATCHERS_GET = { 
			"/produtos/**",
			"/categorias/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes/**"
//			"/auth/forgot/**"
	};
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
//		"/swagger-ui.html", "/webjars/**");
//	}

	//Configuração do httpsecurity
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//se os profiles ativos estiverem no teste, libero o acesso
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		//http.cors() chama o @bean do CorsConfigurationSource / .csrf().disable() fazer defesa
		http.cors().and().csrf().disable();
		//abrir permissão
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		
		//todos que tiverem nesse vetor, irá permitir
			.antMatchers(PUBLIC_MARCHERS).permitAll()
			.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		//backend nao vai criar sessão de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	//É padrão ... quem é o userDetailsService e quem é o de codificação da senha, que no caso é o bCrypt
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	//permitir o acesso aos endpoints por multiplas fontes com as configurações basicas
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	//um Bean de BCryptPasswordEncoder no arquivo de configuração
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
