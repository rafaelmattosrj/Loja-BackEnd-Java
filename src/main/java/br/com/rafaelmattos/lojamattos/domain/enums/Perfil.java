package br.com.rafaelmattos.lojamattos.domain.enums;

////Implementação do Enum
public enum Perfil {

	//ROLE é obrigatorio do spring security
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for (Perfil x : Perfil.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
