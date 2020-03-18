package br.com.codenation.desafio.service;

import org.springframework.http.ResponseEntity;

import br.com.codenation.desafio.Domain.Cifra;

public interface DesafioService {

	public ResponseEntity<Cifra> getObjCodificadoApi();
	public ResponseEntity<Cifra>inserirNovosDados(ResponseEntity<Cifra> objAnswer);
	public  ResponseEntity<String> postarArquivo();

}
