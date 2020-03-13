package br.com.codenation.desafio.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.codenation.desafio.Domain.Cifra;
import br.com.codenation.desafio.Domain.DecifraTexto;
import br.com.codenation.desafio.Domain.GravarArquivoJson;
import br.com.codenation.desafio.Domain.ResumoSHA1;
import br.com.codenation.desafio.service.DesafioService;



@Service
public class DesafioServiceImpl implements DesafioService {
	
	@Autowired
	RestTemplate template;
	
	static String TOKEN = "239977e176f146a1414f17ae145b090bd072b168";
	static String URL = "api.codenation.dev/v1/challenge/dev-ps/generate-data?token=";
	static String FALHA_CONEXAO = "Conexão api Codenation falhou - ";
	static String ERRO_RESUMO = "Não foi possível gerar resumo do texto " ;
	
	@Override
	public ResponseEntity<Cifra> getTextoCodificadoApi() {
		
		ResponseEntity<Cifra> entidadeCifraModelo = null;

		try {
		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(URL+TOKEN).build();	
			entidadeCifraModelo = template.getForEntity(uri.toUriString(), Cifra.class);
			
		}catch(ResourceAccessException e) {
			System.out.println(FALHA_CONEXAO + e.getMessage().toString());		
		}
	
		return entidadeCifraModelo;
	}
	public ResponseEntity<Cifra> inserirNovosDados(ResponseEntity<Cifra> objAnswer){
		
		DecifraTexto decifraTexto = new DecifraTexto();
		decifraTexto.zeraContador();
		String textoDecifrado = decifraTexto.decifrarCesar(objAnswer.getBody().getCifrado());	
		
		objAnswer.getBody().setDecifrado(textoDecifrado);
		objAnswer.getBody().setNumeroCasas(decifraTexto.contadorCasas());
		
		try {
			ResumoSHA1 resumo = new ResumoSHA1();
			objAnswer.getBody().setResumoCriptografico(resumo.resumirTextoDecifrado(textoDecifrado));
			
		} catch (Exception e) { 
			System.out.println(ERRO_RESUMO + e.getMessage().toString());	 
		}
		
		GravarArquivoJson gravarArquivoJson = new GravarArquivoJson();
		gravarArquivoJson.gravarArquivoJson(objAnswer.getBody());
		
		
		return objAnswer;
		
	}
}

