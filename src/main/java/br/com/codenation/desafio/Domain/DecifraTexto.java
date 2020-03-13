package br.com.codenation.desafio.Domain;

public class DecifraTexto {
	static int contCasas;
	
	public String decifrarCesar(String textoCifrado) {
		StringBuilder strBuilder = new StringBuilder(textoCifrado);
	
		for (char let : textoCifrado.toCharArray()) {
		
			if (verificaCaractere(let)) {strBuilder.setCharAt(contCasas,(char) (let + 3));}
			else {
				
				if (let == 'z') {strBuilder.setCharAt(contCasas,'c');}
				if (let == 'y') {strBuilder.setCharAt(contCasas,'b');}
				if (let == 'x') {strBuilder.setCharAt(contCasas,'a');} 
				else {{strBuilder.setCharAt(contCasas,let);}
					 
				}
			}		
			contCasas++;
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
		case 'x': {
			return false;
		}
		case 'y': {
			return false;
		}
		case 'z': {
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
	public int contadorCasas() {
		return contCasas;
	}
	public void zeraContador() {
		 contCasas = 0;
	}
	
	
}

