package br.com.codenation.desafio.Domain;

public class CifraModelo {

	private int numeroCasas;
	private String token;
	private String cifrado;
	private String decifrado;
	private String resumoCriptografico;
	
	public int getNumeroCasas() {
		return numeroCasas;
	}
	public void setNumeroCasas(int contCasas) {
		this.numeroCasas = contCasas;
	}
	public String getToken() {
		return token;
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
	public String getDecifrado() {
		return decifrado;
	}
	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}
	public String getResumoCriptografico() {
		return resumoCriptografico;
	}
	public void setResumoCriptografico(String resumoCriptografico) {
		this.resumoCriptografico = resumoCriptografico;
	}
	
}
