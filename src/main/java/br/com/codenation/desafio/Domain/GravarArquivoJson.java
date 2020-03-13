package br.com.codenation.desafio.Domain;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class GravarArquivoJson {
	
	static String MSG_ERRO_JSON = "ERRO ao gravar arquivo JSON ";
	static String NOME_ARQUIVO = "answer.json";

	public void gravarArquivoJson(Cifra objAnswer) {

		Gson gson = new Gson();
		String json = gson.toJson(objAnswer);

		try {
			FileWriter writeFile = new FileWriter(NOME_ARQUIVO);
			writeFile.write(json);
			writeFile.close();
		} catch (IOException e) {
			System.out.println(MSG_ERRO_JSON + e.getMessage().toString());
		}
		
	}
}
