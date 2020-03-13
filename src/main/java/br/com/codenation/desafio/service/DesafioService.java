package br.com.codenation.desafio.service;

import org.springframework.http.ResponseEntity;

import br.com.codenation.desafio.Domain.CifraModelo;

public interface DesafioService {

	public ResponseEntity<CifraModelo> getTextoCodificadoApi();

}
