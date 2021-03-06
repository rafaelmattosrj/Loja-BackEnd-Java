package br.com.rafaelmattos.lojamattos.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component //injetar em outras classes como componente
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	//gerar token
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				//horario atual do sistema mais o tempo de expiração
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				//como vai assinar o token
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			//pegar o usuario
			String username = claims.getSubject();
			//pegar a expiracao
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			//now.before(expirationDate -> instante atual ainda é anterior a data de expiração
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			//funcao q recupera os cliente apartir de um token
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
