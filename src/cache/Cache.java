package cache;

public class Cache{ // Classe representando a cache
	
	// Atributos
	
	@SuppressWarnings("unused")
	private int n_blocos,
	associatividade,  
	n_palavras_bloco, 
	n_conjuntos;
	private Conjunto[] conj;
	 
	 // Construtor
	 
	public Cache(int n_blocos, int associatividade, int n_palavras_bloco) {
		
		// Inicialização dos valores dos atributos
		
		this.n_blocos         = n_blocos;
		this.associatividade  = associatividade ;
		this.n_palavras_bloco = n_palavras_bloco ;
		this.n_conjuntos      = n_blocos / associatividade ;
		this.conj= new Conjunto[this.n_conjuntos]; 
		
		for(int i = 0; i < n_conjuntos; i++) { // Inicialização dos conjuntos da cache
			
			this.conj[i]= new Conjunto(associatividade, n_palavras_bloco);
		}
	}
	
	// Métodos
	
	public int busca_e_insere_na_cache(int endereco) { // Se houve falha na cache devolve 1 se houve acerto 0
		
		int endereco_palavra,
		 endereco_bloco,
		 indice,
		 tag,
		 i,
		 i_acerto,
		 i_livre,	
		 i_lru,		
		 ordem_min,	
		 ordem_max,
		 result;
		
		endereco_palavra = endereco / 4;
		endereco_bloco   = endereco_palavra / n_palavras_bloco;
		indice           = endereco_bloco   % n_conjuntos;
		tag              = endereco_bloco   / n_conjuntos;
		
		i_acerto = -1;
		i_livre  = -1;
		i_lru    = -1;
		ordem_min  = 2147483647; // relativo ao MAX_INT em C
		ordem_max  = -1;
		
		for (i = 0 ; i < associatividade ; i++){
			
			if (conj[indice].posicao[i].getBit_valid() == 1){ // Se a posição já está ocupada
				
				if (conj[indice].posicao[i].getTag() == tag)
					i_acerto = i ;
				
				if (ordem_max < conj[indice].posicao[i].getOrdem_acesso())
					ordem_max = conj[indice].posicao[i].getOrdem_acesso();
				
				if (ordem_min > conj[indice].posicao[i].getOrdem_acesso()){
					i_lru    = i ;
					ordem_min= conj[indice].posicao[i].getOrdem_acesso();
				}
			}
			else{ // Se a posição já está vazia
				if (i_livre == -1)
					i_livre = i ;
			}
		}
		if (i_acerto != -1) { // Acerto
			
			conj[indice].posicao[i_acerto].setOrdem_acesso(ordem_max + 1);
			result = 0;
		}
		else if (i_livre != -1) { // Falha SEM substitucao
		
			conj[indice].posicao[i_livre].setBit_valid(1);
			conj[indice].posicao[i_livre].setTag(tag);
			conj[indice].posicao[i_livre].setOrdem_acesso(ordem_max + 1);
			result = 1;
		}
		else { // Falha COM substitucao
		
			conj[indice].posicao[i_lru].setTag(tag);
			conj[indice].posicao[i_lru].setOrdem_acesso(ordem_max + 1);
			result = 1;
		}

		return result;
	}
}