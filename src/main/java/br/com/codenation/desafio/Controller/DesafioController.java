package br.com.codenation.desafio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.codenation.desafio.Domain.CifraModelo;
import br.com.codenation.desafio.Domain.DecifraTexto;
import br.com.codenation.desafio.Domain.GravarArquivoJson;
import br.com.codenation.desafio.Domain.ResumoSHA1;
import br.com.codenation.desafio.service.DesafioService;

@RestController
public class DesafioController {
	
	@Autowired
	DesafioService desafioService;
	
	static String ERRO_RESUMO = "Não foi possível gerar resumo do texto " ;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	@GetMapping(value = "/api")
	public ResponseEntity<CifraModelo> decifrarDesafioCodenation() {
	

		ResponseEntity<CifraModelo> entidadeCifraModelo = desafioService.getTextoCodificadoApi();
		
		DecifraTexto decifraTexto = new DecifraTexto();
		decifraTexto.zeraContador();
		String textoDecifrado = decifraTexto.decifrarCesar(entidadeCifraModelo.getBody().getCifrado());	
		
		entidadeCifraModelo.getBody().setDecifrado(textoDecifrado);
		entidadeCifraModelo.getBody().setNumeroCasas(decifraTexto.contadorCasas());
		
		try {
			ResumoSHA1 resumo = new ResumoSHA1();
			entidadeCifraModelo.getBody().setResumoCriptografico(resumo.resumirTextoDecifrado(textoDecifrado));
			
		} catch (Exception e) { 
			System.out.println(ERRO_RESUMO + e.getMessage().toString());	 
		}
		
		GravarArquivoJson gerarArquivo = new GravarArquivoJson();
		gerarArquivo.gravarArquivoJson(entidadeCifraModelo.getBody());

		return ResponseEntity.ok(entidadeCifraModelo.getBody());
	}
}
