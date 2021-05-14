package cache;

public class Posicao { // Classe representando 1 posição de uma cache
	
	// Atributos
	
	private int bit_valid;
	private int tag;
	private int ordem_acesso;
	protected int[] bloco;
	
	// Construtor
	
	public Posicao(int n_palavras_bloco){
		
		this.bit_valid= 0;
		this.tag= 0;
		this.ordem_acesso= 0;
		this.bloco= new int[n_palavras_bloco];
		
		for(int i= 0; i < n_palavras_bloco; i++) { // Inicialização dos arrays de bloco de dados
			this.bloco[i]= 0;
		}
	}
	
	// Setters e getters
	
	public int getBit_valid() {
		return bit_valid;
	}

	public void setBit_valid(int bit_valid) {
		this.bit_valid = bit_valid;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getOrdem_acesso() {
		return ordem_acesso;
	}

	public void setOrdem_acesso(int lRU) {
		ordem_acesso = lRU;
	}
}
