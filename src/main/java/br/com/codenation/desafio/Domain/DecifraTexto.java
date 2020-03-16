package br.com.codenation.desafio.Domain;

public class DecifraTexto {
	
	static int CONT_POSICAO;
	static char lETRAZ = 'z';
	static char lETRAA = 'a';
	
	public String decifrarCesar(String textoCifrado, int numeroCasas) {
		StringBuilder strBuilder = new StringBuilder(textoCifrado);
	
		for (char let : textoCifrado.toCharArray()) {
		
			if (verificaCaractere(let)) {let = substituirLetra(let, numeroCasas);}		
				strBuilder.setCharAt(CONT_POSICAO, let);	
				CONT_POSICAO++;
		}	
		return strBuilder.toString();			
	}
	
	public boolean verificaCaractere(char let) {

		switch (let) {
		case '.': {
			return false;
		}
		case ',': {
			return false;
		}
		case ':': {
			return false;
		}
		case ';': {
			return false;
		}
		case '-': {
			return false;
		}
		case '_': {
			return false;
		}
		case '!': {
			return false;
		}
		case '?': {
			return false;
		}		
		case '\"': {
			return false;
		}
		case ' ': {
			return false;
		}
		default:
			return true;
		}
	}
	public char substituirLetra(char let, int numeroCasa) {
			
		while(let >lETRAA && numeroCasa!=0) { 
			let = (char) (let-1);
			numeroCasa--;		
		}	
		if(numeroCasa != 0) let = (char)(lETRAZ - (numeroCasa-1)); return let;
	}
	public void zeraContador() {
		CONT_POSICAO = 0;
	}
	
}

