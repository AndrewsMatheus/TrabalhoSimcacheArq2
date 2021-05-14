package cache;

public class Conjunto{ // Classe representando um conjunto de uma cache
	
	// Atributos
	
	protected Posicao[] posicao;
	
	// Construtor
	
	public Conjunto(int associatividade, int n_palavras_bloco) {
		this.posicao= new Posicao[associatividade];
		
		for(int i= 0; i < associatividade; i++)
			this.posicao[i]= new Posicao(n_palavras_bloco);
	}
	
	// Setters e getters
	
	public Posicao[] getPosicao() {
		return posicao;
	}

	public void setPosicao(Posicao[] posicao) {
		this.posicao = posicao;
	}
}