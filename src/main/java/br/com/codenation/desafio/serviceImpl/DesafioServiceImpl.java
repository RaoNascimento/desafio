package br.com.codenation.desafio.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
	static String POST_URL = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=";
	static String FILE = "answer.json";
		
	@Override
	public ResponseEntity<Cifra> getObjCodificadoApi() {
		
		ResponseEntity<Cifra> obj = null;

		try {
		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(URL+TOKEN).build();	
		 obj = template.getForEntity(uri.toUriString(), Cifra.class);
		 	
		}catch(ResourceAccessException e) {
			System.out.println(FALHA_CONEXAO + e.getMessage().toString());		
		}
	
		return  obj;
	}
	public ResponseEntity<Cifra> inserirNovosDados(ResponseEntity<Cifra> obj){
		
		DecifraTexto decifraTexto = new DecifraTexto();
		decifraTexto.zeraContador();
		String textoDecifrado = decifraTexto.decifrarCesar(obj.getBody().getCifrado().toLowerCase(), obj.getBody().getNumero_casas());	
		
		obj.getBody().setDecifrado(textoDecifrado);
		
		try {
			ResumoSHA1 resumo = new ResumoSHA1();
			obj.getBody().setResumo_criptografico(resumo.resumirTextoDecifrado(textoDecifrado));
			
		} catch (Exception e) { 
			System.out.println(ERRO_RESUMO + e.getMessage().toString());	 
		}
		
		GravarArquivoJson gravarArquivoJson = new GravarArquivoJson();
		
			gravarArquivoJson.gravarArquivoJson(obj.getBody());
				
		return obj;	
	}
	
	@Override
	public ResponseEntity<String> postarArquivo() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		LinkedMultiValueMap<String, Object> body =  new LinkedMultiValueMap<>();
		 		
		body.add("answer", new FileSystemResource(FILE));
		HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
		ResponseEntity<String> response = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate
					 .postForEntity(POST_URL+TOKEN, requestEntity, String.class);	
			
			System.out.println("RESPOSTA" +response);
			
			return response;
			
		} catch(HttpClientErrorException e) {
			System.out.println(FALHA_CONEXAO + e.getMessage().toString());
			e.printStackTrace();
		}
		return null;
				
	}
 }

	
	

	

