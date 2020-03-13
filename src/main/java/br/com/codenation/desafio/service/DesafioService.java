package br.com.codenation.desafio.service;

import org.springframework.http.ResponseEntity;

import br.com.codenation.desafio.Domain.Cifra;

public interface DesafioService {

	public ResponseEntity<Cifra> getTextoCodificadoApi();
	public ResponseEntity<Cifra>inserirNovosDados(ResponseEntity<Cifra> objAnswer);

}
