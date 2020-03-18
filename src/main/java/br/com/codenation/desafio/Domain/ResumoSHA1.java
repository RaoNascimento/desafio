package br.com.codenation.desafio.Domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResumoSHA1 {
	
	static String ERRO_SHA1 = " ERRO AO GERAR SHA-1";

	public String resumirTextoDecifrado(String textoDecifrado) throws NoSuchAlgorithmException { 
		try {  
            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
            byte[] messageDigest = md.digest(textoDecifrado.getBytes()); 
 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
 
            return hashtext; 
		}
        catch (NoSuchAlgorithmException e) { 
        	System.out.println(ERRO_SHA1 + e.getMessage().toString());
            throw new RuntimeException(e); 
        } 
        
	}
}
