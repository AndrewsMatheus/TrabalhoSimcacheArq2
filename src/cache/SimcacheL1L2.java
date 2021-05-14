package cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimcacheL1L2 {

public static void main(String[] args) throws IOException{
			
		if (args.length != 2) { // Checa se os parâmetros foram passados na execução do programa
			
			System.out.println("Uso: cache.simcacheL1L2 arquivo_configuracaoL1L2 arquivo_acessos");
			System.exit(0); // Força término do programa
		}
		
		int n_blocos,			
		 associatividade,	
		 n_palavras_bloco,	
		 n_acessos_cacheL1,	
		 n_falhas_cacheL1,	
		 n_acessos_cacheL2,	
		 n_falhas_cacheL2,	
		 result_acessoL1,
		 result_acessoL2,
		 endereco;
		
		try {
			
			String S_ler; // Variável de leitura de arquivo
			
			// Lê arquivo de configuração para as caches L1 e L2
			
			FileReader configL1L2= new FileReader(args[0]);
			BufferedReader lerConfL1l2= new BufferedReader(configL1L2);
			
			S_ler= lerConfL1l2.readLine();
			n_blocos= Integer.parseInt(S_ler);
			
			S_ler= lerConfL1l2.readLine();
			associatividade= Integer.parseInt(S_ler);
			
			S_ler= lerConfL1l2.readLine();
			n_palavras_bloco= Integer.parseInt(S_ler);
			
			// Criação da cache L1
			Cache cacheL1= new Cache(n_blocos, associatividade, n_palavras_bloco);
			
			S_ler= lerConfL1l2.readLine();
			n_blocos= Integer.parseInt(S_ler);
			
			S_ler= lerConfL1l2.readLine();
			associatividade= Integer.parseInt(S_ler);
			
			S_ler= lerConfL1l2.readLine();
			n_palavras_bloco= Integer.parseInt(S_ler);
			
			Cache cacheL2= new Cache(n_blocos, associatividade, n_palavras_bloco);
			
			lerConfL1l2.close(); // Fecha arquivo de configuração da cache L2
			
			// Lê arquivo de trace com os endereços acessados pelo programa
			
			FileReader trace= new FileReader(args[1]);
			BufferedReader lerTrace = new BufferedReader(trace);
			
			// Seta variáveis de acesso e falha das caches L1 e L2
			
			n_acessos_cacheL1 = 0;
			n_falhas_cacheL1  = 0;
			
			n_acessos_cacheL2 = 0;
			n_falhas_cacheL2  = 0;
			
			
			S_ler = lerTrace.readLine(); //Lê o primeiro endereço do arquivo de trace
			endereco= Integer.parseInt(S_ler); // Converte o endereço lido de string para inteiro
			
			while(S_ler!= null){ // Lê arquivo até ele acabar
				
				result_acessoL1 = cacheL1.busca_e_insere_na_cache(endereco); // Checa se o endereço está contido na cache L1
				n_acessos_cacheL1++;
				
				if (result_acessoL1 == 1){ // Se houver falha na cache L1
					
					n_falhas_cacheL1 ++;
					
					result_acessoL2 = cacheL2.busca_e_insere_na_cache(endereco); // Checa se o endereço está contido na cache L1
					
					n_acessos_cacheL2++;
					
					if(result_acessoL2 == 1) // Se houver falha na cache L2
						n_falhas_cacheL2++;
				}
				
				S_ler = lerTrace.readLine(); //Lê o próximo endereço do arquivo de trace
				
				if(S_ler != null) // Se o dado lido for null (fim do arquivo) não converte para inteiro
					endereco= Integer.parseInt(S_ler);
			}
			
			// Imprime estatísticas de acesso e falha das caches
			
			System.out.printf("%10d %10d\n", n_acessos_cacheL1, n_falhas_cacheL1);
			System.out.printf("%10d %10d\n", n_acessos_cacheL2, n_falhas_cacheL2);
			
			lerTrace.close(); // Fecha arquivo de trace
			
		}catch (IOException e) { // Tratamento de excessão para arquivo inválido
			
		        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		        System.exit(0); //Força término do programa
		}
	}

}
