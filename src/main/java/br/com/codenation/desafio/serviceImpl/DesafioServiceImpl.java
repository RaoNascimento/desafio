package br.com.codenation.desafio.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.codenation.desafio.Domain.CifraModelo;
import br.com.codenation.desafio.service.DesafioService;

@Service
public class DesafioServiceImpl implements DesafioService {
	
	@Autowired
	RestTemplate template;
	
	static String TOKEN = "239977e176f146a1414f17ae145b090bd072b168";
	static String URL = "api.codenation.dev/v1/challenge/dev-ps/generate-data?token=";
	static String FALHA_CONEXAO = "Conexão api Codenation falhou - ";
	
	@Override
	public ResponseEntity<CifraModelo> getTextoCodificadoApi() {
		
		ResponseEntity<CifraModelo> entidadeCifraModelo = null;

		try {
		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(URL+TOKEN).build();	
			entidadeCifraModelo = template.getForEntity(uri.toUriString(), CifraModelo.class);
			
		}catch(ResourceAccessException e) {
			System.out.println(FALHA_CONEXAO + e.getMessage().toString());		
		}
	
		return entidadeCifraModelo;
	}

}
