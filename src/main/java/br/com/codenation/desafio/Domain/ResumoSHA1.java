package br.com.codenation.desafio.Domain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResumoSHA1 {
	
	static String ERRO_SHA1 = " ERRO AO GERAR SHA-1";
	
	public String resumirTextoDecifrado(String textoDecifrado) throws NoSuchAlgorithmException { 
	MessageDigest algoritmo = MessageDigest.getInstance("SHA-1");
	 StringBuilder resumoBuilder = null;
	
	 try {
		byte arrayGerado[] = algoritmo.digest(textoDecifrado.getBytes("UTF-8"));
		 resumoBuilder = new StringBuilder();
		 
		 for (byte let :  arrayGerado) {
	         resumoBuilder.append(String.format("%02X", 0xFF & let));
	       }
				
	} catch (UnsupportedEncodingException e) {

		System.out.println(ERRO_SHA1 + e.getMessage().toString());
	}
	 return resumoBuilder.toString();
	}

}
