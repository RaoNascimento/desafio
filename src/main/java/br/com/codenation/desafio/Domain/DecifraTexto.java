package br.com.codenation.desafio.Domain;

public class DecifraTexto {
	
	static int contPosicao;
	static char letraZ = 'z';
	static char letraA = 'a';
	
	public String decifrarCesar(String textoCifrado, int numeroCasas) {
		StringBuilder strBuilder = new StringBuilder(textoCifrado);
	
		for (char let : textoCifrado.toCharArray()) {
		
			if (verificaCaractere(let)) {let = substituirLetra(let, numeroCasas);}		
				strBuilder.setCharAt(contPosicao, let);	
				contPosicao++;
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
			
		while(let >letraA && numeroCasa!=0) {
			
			let = (char) (let-1);
			numeroCasa--;		
		}	
		if(numeroCasa != 0) {let = (char)(letraZ - (numeroCasa-1));}
		return let;
	}
	public void zeraContador() {
		contPosicao = 0;
	}
	
}

