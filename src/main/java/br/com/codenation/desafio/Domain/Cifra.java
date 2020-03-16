package br.com.codenation.desafio.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Cifra {

	@JsonProperty("numero_casas")
	private int numeroCasas;
	
	@JsonProperty("token")
	private String token;
	
	@JsonProperty("cifrado")
	private String cifrado;
	
	@JsonProperty("decifrado")
	private String decifrado;
	
	@JsonProperty("resumo_criptografico")
	private String resumoCriptografico;
	
	
	public int getNumeroCasas() {
		return numeroCasas;
	}
	public void setNumeroCasas(int numeroCasas) {
		this.numeroCasas = numeroCasas;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getCifrado() {
		return cifrado;
	}
	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}
	
	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}
	
	public void setResumoCriptografico(String resumoCriptografico) {
		this.resumoCriptografico = resumoCriptografico;
	}
	
}
