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
	//static String TOKEN = "6db25cf8b7c9430b108cc9f9d20e4354cc00276b";
	static String URL = "api.codenation.dev/v1/challenge/dev-ps/generate-data?token=";
	static String FALHA_CONEXAO = "Conexão api Codenation falhou - ";
	static String ERRO_RESUMO = "Não foi possível gerar resumo do texto " ;
	
	@Override
	public ResponseEntity<Cifra> getObjCodificadoApi() {
		
		ResponseEntity<Cifra> obj = null;

		try {
		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(URL+TOKEN).build();	
		 obj = template.getForEntity(uri.toUriString(), Cifra.class);
		 System.out.println(" CASAS: "+obj.getBody().getNumeroCasas());
		 	
		}catch(ResourceAccessException e) {
			System.out.println(FALHA_CONEXAO + e.getMessage().toString());		
		}
	
		return  obj;
	}
	public ResponseEntity<Cifra> inserirNovosDados(ResponseEntity<Cifra> obj){
		
		DecifraTexto decifraTexto = new DecifraTexto();
		decifraTexto.zeraContador();
		String textoDecifrado = decifraTexto.decifrarCesar(obj.getBody().getCifrado().toLowerCase(), obj.getBody().getNumeroCasas());	
		
		obj.getBody().setDecifrado(textoDecifrado);
		//obj.getBody().setNumeroCasas(decifraTexto.contadorCasas());
		
		try {
			ResumoSHA1 resumo = new ResumoSHA1();
			obj.getBody().setResumoCriptografico(resumo.resumirTextoDecifrado(textoDecifrado));
			
		} catch (Exception e) { 
			System.out.println(ERRO_RESUMO + e.getMessage().toString());	 
		}
		
		GravarArquivoJson gravarArquivoJson = new GravarArquivoJson();
		gravarArquivoJson.gravarArquivoJson(obj.getBody());
				
		return obj;
		
	}
}

