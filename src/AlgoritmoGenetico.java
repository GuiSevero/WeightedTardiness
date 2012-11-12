import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class AlgoritmoGenetico {       
	
	private ArrayList<Cromossomo> _population = new ArrayList<Cromossomo>();
	private ArrayList<Cromossomo> _elite = new ArrayList<Cromossomo>();
	private String _document;
	private BufferedWriter _arquivoSaida;
	private int _minutosMaximo = 1;
	private int _maxPopulationSize = 200;
	private int _tamanhoElite = 25;
	private Cromossomo _melhorSolucao;
	private int _popInicial = 25;
	private String _inicioDoAlgoritmo;
	private String _ultimaMelhorSolucaoEncontrada;
	
	/**
	 * 
	 * @param populacaoInicial - tamanho da população inicial
	 * @param tempo - tempo maximo
	 * @param doc - arquivo de entrada
	 */
	public AlgoritmoGenetico(int populacaoInicial, int tempo, String doc){
		if(populacaoInicial >= _tamanhoElite){
			this._popInicial = populacaoInicial;
		}
		
		if(tempo > _minutosMaximo){
			_minutosMaximo = tempo;
		}
		
		this._document = doc;
	}
	
	
	/**
	 * 
	 * @param pai - Cromossomo Pai
	 * @param mae - Cromossomo Filho
	 * @return Cromossomo[] populacao gerada - filho1 e filho2
	 */
	public Cromossomo crosOver(Cromossomo pai, Cromossomo mae){
		ArrayList<Gene> novosGenes = new ArrayList<Gene>();
		int numItens = pai.length();
		//Array de descendentes
		Cromossomo filho = new Cromossomo(numItens);
		//Instancia Filhos e ponto de quebra
    	
    	//Ponto de quebra do cross-over
        int quebra = (int)((numItens - 1)*Math.random()); // sorteia ponto de quebra

        
        // primeira parte dos filhos
        for (int i = 0; i < quebra; i++){
            filho.setGen(i, pai.getGen(i).clone());
        }

        // segunda parte dos filhos
        //TODO resolver problema de pegar gens da mae
        
        for(int i=0; i < numItens ; i++){
        	int achou=0;
        	for(int j=0; j < quebra; j++){
        		if(filho.getGen(j).equals(mae.getGen(i))){
        			achou++;
        		}
        	}
        	
    		if(achou == 0){
    			novosGenes.add(mae.getGen(i).clone());
    		}
    			
    	}
        
        for (int i = quebra, j=0; i < numItens; i++, j++){
        	filho.setGen(i, novosGenes.get(j));
        }
        
        
        //Avalia os filhos
        filho.eval();
        
        /* /Adiciona os descendentes
        descendentes[0] = filho1;
        descendentes[1] = filho2;
        return descendentes;
        */
        return filho;
        
	}
	
	/**
	 * Esta sobrecarga do cross-over faz uma variação de apenas um pai
	 * @param pai
	 * @return
	 */
	
	public Cromossomo crosOver(Cromossomo pai){
		
		int numItens = pai.length();
		//Array de descendentes
		Cromossomo filho = new Cromossomo(numItens);
		//Instancia Filhos e ponto de quebra
    	
    	//Ponto de quebra do cross-over
        int quebra = (int)((numItens - 1)*Math.random()); // sorteia ponto de quebra

        
        // primeira parte dos filhos
        for (int i = 0; i <= quebra; i++){
            filho.setGen(i, pai.getGen(i).clone());
        }

        // segunda parte dos filhos
        //TODO resolver problema de pegar gens da mae
        for (int i = quebra, j=1; i < numItens; i++, j++){
        		filho.setGen(i, pai.getGen(numItens  - j).clone());
        }
        
        
        //Avalia os filhos
        filho.eval();
        
        /* /Adiciona os descendentes
        descendentes[0] = filho1;
        descendentes[1] = filho2;
        return descendentes;
        */
        return filho;
        
	}
	
	public void sort(){
		Collections.sort(_population);
	}

	/**
	 * Gera uma população inicial de cromossomos.
	 * @param n - Numero de indivíduos a ser gerado
	 * @param document - arquivo de uma instancia de um problema para servir de semente
	 */
	public void generatePopulation(){
		
		//TODO - FORMATAR SAIDA PARA PADRAO "ENTRADA" - compara com instancias
		
		//Cria um cromossomo semente
		Cromossomo seed = new Cromossomo(_document);
		//shuffleGens(seed);
		seed.eval();
		_melhorSolucao = seed;
		
		//adiciona a semente
		_population.add(seed);
		
		//Cria novos elementos para a população
		for(int i=0; i < _popInicial -1; i++){
			
			//Copia o cromossomo semente
			//Cromossomo filho = seed.clone();
			Cromossomo filho = crosOver(seed);
			
			//Muta os genes do filho com probabilidade 1/n
			filho.mutacao(_population.size());
			
			//Randomiza os genes
			shuffleGens(filho);
			
			//Avalia o filho
			filho.eval();
			
			//Adiciona a populaçãos
			_population.add(filho);
		}
		
		Collections.sort(_population);
		for(int i=0; i < _tamanhoElite; i++){
			_elite.add(_population.get(i));
		}
		Collections.sort(_elite);
		_melhorSolucao = _elite.get(0);
		
		
	}
	
	
	/**
	 * Gera uma população inicial de cromossomos.
	 * @param n - Numero de indivíduos a ser gerado
	 * @param document - arquivo de uma instancia de um problema para servir de semente
	 */
	public void generatePopulation(Cromossomo seed){
		
		//TODO - FORMATAR SAIDA PARA PADRAO "ENTRADA" - compara com instancias
		
		//Cria um cromossomo semente
		Cromossomo seedAux = seed.clone();
		shuffleGens(seedAux);
		seed.eval();
		
		//adiciona a semente
		_population.add(seedAux);
		
		//Cria novos elementos para a população
		for(int i=0; i < _popInicial -1; i++){
			
			//Copia o cromossomo semente
			Cromossomo filho = seed.clone();
			
			//Muta os genes do filho com probabilidade 1/n
			filho.mutacao(_population.size());
			
			//Randomiza os genes
			shuffleGens(filho);
			
			//Avalia o filho
			filho.eval();
			
			//Adiciona a populaçãos
			_population.add(filho);
		}
		
	}
	
	/**
	 * Gera uma população familia
	 * @param n
	 * @param document
	 */
	public void generateFamily(int n, String document){
		
		//Seta o arquivo usado
		this._document = document;
		this._popInicial = n;
		
		//Gera ancestrais aleatórios
		this.generatePopulation();
		//Ordena os ancestrais
		this.sort();
		
		
		//Cria um cromossomo semente
		Cromossomo seed = new Cromossomo(document);
		//adiciona a semente
		_population.add(seed);
		
		//Cria novos elementos para a população
		for(int i=0; i < (n -1) / 2; i++){
			
			//Copia o cromossomo semente
			shuffleGens(_population.get(1));
			Cromossomo filho = this.crosOver(_population.get(0), _population.get(1));
			
			
			//Adiciona a populaçãos
			_population.add(filho);
			shuffleGens(filho);
			_population.add(filho);
			this.sort();
		}
	}
	
	
	 /**
	  * Faz o shuffle dos gens de um cromossomo
	  * @param a
	  */
	 public  void shuffleGens(Cromossomo a) {
			int n = a.length();
			Random random = new Random();
			random.nextInt();
			for (int i = 0; i < n; i++) {
				int change = i + random.nextInt(n - i);
				swap(a, i, change);
			}
			a.eval();
		}
	    
	 /**
	  * Faz o swap de dois genes
	  * @param a - Cromossomo
	  * @param i - Gene a ser trocado com "change"
	  * @param change
	  */
	    protected void swap(Cromossomo a, int i, int change) {
			Gene helper = a.getGen(i);
			a.setGen(i, a.getGen(change));
			a.setGen(change, helper);
		}
	    
	    /**
	     * Retorna um determinado cromossomo da população
	     * @param i
	     * @return
	     */
	    public Cromossomo getCromossomo(int i){
	    	return this._population.get(i);
	    }
	    
	    /**
	     * Retorna o tamanho da população
	     * @return
	     */
	    public int populationSize(){
	    	return this._population.size();
	    }
	    
	    /**
	     * seleciona um numero determinado para ser a elite
	     */
	    public void selecionaElite(){
	    	Collections.sort(_population);
			for(int i=0; i < _tamanhoElite; i++){
				_elite.set(i, _population.get(i));
			}
			Collections.sort(_elite);
	    }
	    
	    /**
	     * 
	     * @param arquivo
	     * @throws Exception
	     */
	    public void Executar(String arquivo) throws Exception{                                                   
	    	 // arquivo de saída
	        _arquivoSaida = new BufferedWriter(new FileWriter(arquivo));   
	        
	        _inicioDoAlgoritmo = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
	        
	        System.out.println("Algoritmo iniciou para o arquivo " + _document + " as " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));                
	        System.out.println("Aguarde...");
	        long inicio = System.currentTimeMillis();        
	        int populacao = 0;
	        
	        
	        //Ordena de forma crescente
	        this.sort();
	        //Pega a melhor solucao
	        _melhorSolucao = _population.get(0);
	        
	        // inicialização do algoritmo
	        while (!PararGA(inicio, populacao)){ // se não satisfazer critério de parada
	        	//this.generateFamily(populacao, _document);
	        	
	        	
	        	Cromossomo[] filho = new Cromossomo[5];
	        	
	        	//Gera um filho baseado no cross-over de apenas um pai
	        	filho[0] = crosOver(selecionaPai()).mutacao(_population.size());
	        	filho[0].eval();
	        	
	        	//Cria um filho com o cross-over de 2 pais
	        	filho[3] = crosOver(selecionaPai(), selecionaMae()).mutacao(_population.size());
	        	filho[3].eval();
	        	
	        	//Cria um filho com o cross-over de 2 pais com probabilidade de mutação de 100%
	        	filho[4] = crosOver(selecionaPai(), selecionaMae()).mutacao(1);
	        	filho[4].eval();
	        	
	        	
	        	//Cria um filho randômico
	        	filho[1] = _melhorSolucao.clone().mutacao(_population.size());
	        	shuffleGens(filho[1]);
	        	filho[1].eval();
	        	
	        	//Cria um filho baseado na mutação da melhor solução
	        	filho[2] = _melhorSolucao.clone().mutacao(1);
	        	filho[2].eval();
	        		
	        	
	        	//Coloca na elite caso seja uma boa solucao
	        	for(int i=0; i < filho.length; i++){
	        		
	        		//Verifica se é melhor que a melhor solucao atual
	        		if(filho[i].getValue() < _melhorSolucao.getValue()){
		        		_melhorSolucao = filho[i];
		        		System.out.println("Nova melhor solucao: " + _melhorSolucao.getValue());
		        		_ultimaMelhorSolucaoEncontrada = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
		        	}
	        		
	        		//Se for melhor que alguem da elite ele entra pra elite
	        		if(filho[i].getValue() < _elite.get(_elite.size() -1).getValue()){
	        			_elite.set(_elite.size() -1, filho[i]);
	        			//reordena elite
		        		Collections.sort(_elite);
	        			
	        		}
	        		
	        	}
	        	
	        	
	        	if(_population.size() < _maxPopulationSize){
	        		//Adiciona novos elementos aumentando a população
	        		for(int i=0; i < filho.length; i++){
		        		_population.add(filho[i]);
		        	}
	        		
	        	}else{
	        		/*
	        		//Adiciona os filhos gerados sem aumentar a população
		        	for(int i=0; i < filho.length; i++){
		        		_population.set(_population.size() -i -1, filho[i]);
		        	}
		        	*/
		        	
		        	_population.clear();
		        	_population.addAll(_elite);
		        	generatePopulation(_melhorSolucao);
		        	
	        		/*
	        		Cromossomo melhor = _melhorSolucao;
	        		//Gera uma população nova
	        		_population.clear();
	        		_melhorSolucao = melhor;
	        		//Gera uma nova população a partir da melhor solução encontrada como semente
	        		generatePopulation(_melhorSolucao);
	        		*/
	        	}
	        	
	        	
	        	selecionaElite();
	            populacao ++; 
	            
	        }            
	        System.out.println("Melhor Solucao Encontrada: " + _melhorSolucao.getValue());
	        System.out.println("Algoritmo terminou para o arquivo " + _document + " as " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()));                             
	        _arquivoSaida.close();
	        
	    }
	    
	    
	 // Critério de parada do algoritmo: tempo, número de iterações ou solução igual a todos no mesmo bin
	    private boolean PararGA(long inicio, int geracoes) throws Exception {                            
	        // procura pela melhor solução até o momento
	    
	        
	        float minutos = ((System.currentTimeMillis() - inicio)/(float)1000/(float)60);
	        this.sort();
	        // se passou do tempo ou do número de iterações, ou encontrou todos no mesmo bin como melhor solução
	       // if (minutos > _minutosMaximo || populacao > NumeroIteracoesMaximo || MelhorEncontrado == InstanciaBPPC.getNumeroItens() - 1){
        	if ((minutos > _minutosMaximo) || (_population.get(0).getValue() == 0) ){
	            _arquivoSaida.write("Algoritmo Genético");                        
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Arquivo: " + _document);            
	            _arquivoSaida.newLine();    
	            _arquivoSaida.write("Melhor valor encontrado: \n\n" + _melhorSolucao.getValue());
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Tempo máximo em minutos: " + _minutosMaximo);
	            _arquivoSaida.newLine();
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Algoritmo iniciou em: \n\n" + _inicioDoAlgoritmo);
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Melhor solução em: \n\n" + _ultimaMelhorSolucaoEncontrada);
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Número de cromossosmos: " + _population.size());
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Número de cromossosmos iniciais: " + _popInicial);
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Tamanho da elite: " + _tamanhoElite);
	            _arquivoSaida.newLine();
	            _arquivoSaida.write("Melhor solução encontrada: \n\n" + _melhorSolucao.toString());
	           
	            return true;
	        }
	        
	        return false;                 
	    }   
	    
	    /**
	     * Sorteia um cromossomo da população elite
	     * @return
	     */
	    private Cromossomo selecionaPai(){
	    	//Seleciona um pai randomico na elite
	    	return _elite.get((int)((_elite.size() - 1)*Math.random()));
	    	
	    }
	    /**
	     * Sorteia um cromossomo da população inteira
	     * @return
	     */
	    private Cromossomo selecionaMae(){
	    	//Seleciona um pai randomico na elite
	    	return _population.get((int)((_population.size() - 1)*Math.random()));
	    	
	    }
	    
	
}
