package cache;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class SimcacheL1 {

	public static void main(String[] args) throws IOException{
			
		if (args.length != 2) { // Checa se os parâmetros foram passados na execução do programa
			
			System.out.println("Uso: cache.simcacheL1 arquivo_configuracao arquivo_acessos");
			
			System.exit(0); // Força término do programa
		}
		
		int n_blocosL1,			
		 associatividadeL1,	
		 n_palavras_blocoL1,	
		 n_acessos_cacheL1,	
		 n_falhas_cacheL1,	
		 result_acesso,
		 endereco;
		
		try {
			String S_ler; // Variável de leitura de arquivo
			
			// Lê arquivo de configuração
			
			FileReader config= new FileReader(args[0]);
			BufferedReader lerConf= new BufferedReader(config);
			
			S_ler= lerConf.readLine();
			n_blocosL1= Integer.parseInt(S_ler);
			
			S_ler= lerConf.readLine();
			associatividadeL1= Integer.parseInt(S_ler);
			
			S_ler= lerConf.readLine();
			n_palavras_blocoL1= Integer.parseInt(S_ler);
			
			// Criação de cache L1
			Cache cache= new Cache(n_blocosL1, associatividadeL1, n_palavras_blocoL1);
			
			lerConf.close(); // Fecha arquivo de configuração
			
			// Lê arquivo de trace com os endereços acessados pelo programa
			
			FileReader trace= new FileReader(args[1]);
			BufferedReader lerTrace = new BufferedReader(trace);
					
			// Seta variáveis de acesso e falha da cache L1
			
			n_acessos_cacheL1 = 0;
			n_falhas_cacheL1  = 0;
			
			S_ler = lerTrace.readLine(); //Lê o primeiro endereço do arquivo de trace
			endereco= Integer.parseInt(S_ler); // Converte o endereço lido de string para inteiro
			
			while(S_ler!= null){ // Lê arquivo até ele acabar

				result_acesso = cache.busca_e_insere_na_cache(endereco); // Checa se o endereço está contido na cache
				n_acessos_cacheL1++; 
				
				if (result_acesso == 1){ // Se houver falha na cache L1
					
					n_falhas_cacheL1 ++;
				}
				
				S_ler = lerTrace.readLine(); // Lê próximo endereço no arquivo
				
				if(S_ler != null) // Se o dado lido for null (fim do arquivo) não converte para inteiro
					endereco= Integer.parseInt(S_ler);
			}
			
			System.out.printf("%10d %10d\n", n_acessos_cacheL1, n_falhas_cacheL1); // Imprime estatísticas de acesso e falha da cache
			
			lerTrace.close(); // Fecha arquivo de trace
			
		}catch (IOException e) { // Tratamento de excessão para arquivo inválido
			
		        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		        
		        System.exit(0); // Força término do programa
		}
	}
}