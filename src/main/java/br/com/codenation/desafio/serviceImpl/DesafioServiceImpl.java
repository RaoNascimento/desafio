package br.com.codenation.desafio.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	public ResponseEntity<Cifra> postarArquivo() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		GravarArquivoJson ler = new GravarArquivoJson();
		
		String file = "answer.json";
		byte[] content = null;
		

			try {
				content =  ler.readFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			LinkedMultiValueMap<String, Object> body =  new LinkedMultiValueMap<>();
		 
		
		// byte[] answer = readBytesFromFile(content);
		
		body.add("answer", content);
		HttpEntity<MultiValueMap<String, Object>> requestEntity
		 = new HttpEntity<>(body, headers);
	
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Cifra> response = restTemplate
					 .postForEntity("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token="+TOKEN, requestEntity, Cifra.class);				
			return response;
			
		} catch(HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
				
		
	}
	 private static byte[] readBytesFromFile(String content) {

	        FileInputStream fileInputStream = null;
	        byte[] bytesArray = null;

	        try {

	            File file = new File(content);
	            bytesArray = new byte[(int) file.length()];

	            //read file into bytes[]
	            fileInputStream = new FileInputStream(file);
	            fileInputStream.read(bytesArray);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fileInputStream != null) {
	                try {
	                    fileInputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }

	        }

	        return bytesArray;

	    }
	
	/*
	@Override
	public ResponseEntity<Cifra> postarArquivo() {
		//HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 MultiValueMap headers = new LinkedMultiValueMap(); 
		
		Map map =  new HashMap();
		 map.put("Content-Type", "multipart/form-data"); 
		GravarArquivoJson ler = new GravarArquivoJson();
		headers.setAll(map);
		
		
		String content = null;
		try {
			content = ler.readFile("answer.json", StandardCharsets.UTF_8);
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map req_payload = new HashMap();
		req_payload.put("answer", content);
		 HttpEntity request = new HttpEntity<>(req_payload, headers); 
		
		
		//HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		ResponseEntity response = template
		  .postForEntity("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token="+TOKEN, request, Cifra.class);
		
		return response;

}
*/

	
}
	


