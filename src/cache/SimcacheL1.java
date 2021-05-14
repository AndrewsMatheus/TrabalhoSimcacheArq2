package cache;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class SimcacheL1 {

	public static void main(String[] args) throws IOException{
			
		if (args.length != 2) { // Checa se os par�metros foram passados na execu��o do programa
			
			System.out.println("Uso: cache.simcacheL1 arquivo_configuracao arquivo_acessos");
			
			System.exit(0); // For�a t�rmino do programa
		}
		
		int n_blocosL1,			
		 associatividadeL1,	
		 n_palavras_blocoL1,	
		 n_acessos_cacheL1,	
		 n_falhas_cacheL1,	
		 result_acesso,
		 endereco;
		
		try {
			String S_ler; // Vari�vel de leitura de arquivo
			
			// L� arquivo de configura��o
			
			FileReader config= new FileReader(args[0]);
			BufferedReader lerConf= new BufferedReader(config);
			
			S_ler= lerConf.readLine();
			n_blocosL1= Integer.parseInt(S_ler);
			
			S_ler= lerConf.readLine();
			associatividadeL1= Integer.parseInt(S_ler);
			
			S_ler= lerConf.readLine();
			n_palavras_blocoL1= Integer.parseInt(S_ler);
			
			// Cria��o de cache L1
			Cache cache= new Cache(n_blocosL1, associatividadeL1, n_palavras_blocoL1);
			
			lerConf.close(); // Fecha arquivo de configura��o
			
			// L� arquivo de trace com os endere�os acessados pelo programa
			
			FileReader trace= new FileReader(args[1]);
			BufferedReader lerTrace = new BufferedReader(trace);
					
			// Seta vari�veis de acesso e falha da cache L1
			
			n_acessos_cacheL1 = 0;
			n_falhas_cacheL1  = 0;
			
			S_ler = lerTrace.readLine(); //L� o primeiro endere�o do arquivo de trace
			endereco= Integer.parseInt(S_ler); // Converte o endere�o lido de string para inteiro
			
			while(S_ler!= null){ // L� arquivo at� ele acabar

				result_acesso = cache.busca_e_insere_na_cache(endereco); // Checa se o endere�o est� contido na cache
				n_acessos_cacheL1++; 
				
				if (result_acesso == 1){ // Se houver falha na cache L1
					
					n_falhas_cacheL1 ++;
				}
				
				S_ler = lerTrace.readLine(); // L� pr�ximo endere�o no arquivo
				
				if(S_ler != null) // Se o dado lido for null (fim do arquivo) n�o converte para inteiro
					endereco= Integer.parseInt(S_ler);
			}
			
			System.out.printf("%10d %10d\n", n_acessos_cacheL1, n_falhas_cacheL1); // Imprime estat�sticas de acesso e falha da cache
			
			lerTrace.close(); // Fecha arquivo de trace
			
		}catch (IOException e) { // Tratamento de excess�o para arquivo inv�lido
			
		        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		        
		        System.exit(0); // For�a t�rmino do programa
		}
	}
}