import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Cromossomo implements Comparable<Cromossomo> {
	
	private Gene[] _gens;
	private int _value = -1;
	private int length = 0;
	private boolean _valid = true;
	
	/**
	 * Construtoi um novo cromossomo a partir do arquivo especificado por parametro
	 * Será uma instancia do problema de otimização
	 * @param document
	 */
	public Cromossomo(String document){
		try {
			this.fetchDocument(document);
			this.eval();
		} catch (IOException e) {
			new Exception("Erro ao abrir arquivo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Constroi um novo cromossomo alocando o número de genes passado por parametro
	 * @param gens
	 */
	public Cromossomo (int gens){
		//Aloca os genes para este cromossomo
		this._gens = new Gene[gens];
		this.length = gens;
	}
	
	
	/**
	 * retorna os genes deste cromossomos
	 * @return
	 */
	public Gene[] getGens(){
		return _gens;
	}
	
	/**
	 * seta os genes deste cromossomos
	 * @param Gene[] gens
	 */
	public void setGens(Gene[] gens){
		for(int i=0; i < gens.length; i++)
			this._gens[i] = gens[i].clone();
	}
	
	/**
	 * retorna o gene da posicao i do cromossomo
	 * @param i
	 * @return Gene
	 */
	public Gene getGen(int i){
		return _gens[i];
	}
	
	/**
	 * Seta o gene g na posição i
	 * @param i
	 * @param g
	 */
	public void setGen(int i,Gene g){
		 _gens[i] = g;
	}
	
	/**
	 * retorna o valor avaliado para este cromossomo
	 * @return
	 */
	public int getValue(){
		return _value;
	}
	
	
	/**
	 * retorna o numero de genes deste cromossomo
	 * @return
	 */
	public int length(){
		return _gens.length;
	}
	
	/**
	 * Avalia o cromossomo em termos do jobTime, lateness, e seu peso
	 * 
	 */
	public void eval(){
		
		//inicia valor de avalização
    	_value = 0;
    	
    	//Calcula o job time do gene i
    	for(int i=0 ; i < this.length ; i++){
    		
    		int jTime = 0; //Acumulador para o jobTime do gene 
    		for(int j=0; j < i; j++){
    			jTime += _gens[j].time;
    		}
    		//Seta o job time do gene i
    		_gens[i].jobTime = jTime + _gens[i].time;
    		
    		//Calcula o lateness do gene i - maximo entre 0 e a diferença do tempo obtido e do esperado
    		_gens[i].jobLateness = Math.max(_gens[i].jobTime - _gens[i].dueDate, 0);
    		
    		//Avalia a qualidade do cromossomo em relação ao Lateness e os pesos de cada gen
    		_value += _gens[i].jobLateness * _gens[i].weight;
    	}
			
	}
	
	/**
	 * Carrega um documento padrão de entrada e atribui seus genes neste cromossomo
	 * @param document
	 * @throws IOException
	 */
	 protected void fetchDocument(String document) throws IOException{
 		ArrayList<Integer> pars = new ArrayList<Integer>();
 	   BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(document)));
        String[] splitedLine;
 	   
        // linha com capacidade e número de itens
        String linha = bReader.readLine();
        while (linha != null)
        {  
     	   //Split da linha em um arary com todos os números
     	   splitedLine = linha.split(" ");
     	   //Para cada numero do array, adiciona na lista de números
     	   for(String s:splitedLine){
     		   pars.add(Integer.parseInt(s));
     	   }
     	   
     	   //lê a proxima linha
     	   linha = bReader.readLine();
        }
        bReader.close();
        	this.length = pars.size() / 3;
        	
       
        //Instancia todos os genes
   
        this._gens = new Gene[this.length];
        
        for(int i=0;i < pars.size() / 3; i++){
        	//Instancia e aloca cada gene
        	_gens[i] = new Gene();
     	   _gens[i].time = pars.get(i);
        }
        
        //Instancia todos os pesos
        for(int i = pars.size() / 3, j=0; i < 2 * pars.size() / 3; i++, j++){
     	   _gens[j].weight = pars.get(i);
        }
        
        //Instancia todos os due dates
        for(int i = 2 * pars.size() / 3, j=0;i < pars.size(); i++, j++){
     	  _gens[j].dueDate = pars.get(i);
        }
        
	 }
	 
	/**
	 * Faz a cópia de um cromossomo
	 * @return Cromossomo - Cromossomo clonado
	 */
	 public Cromossomo clone(){
		 
		  //Instancia um novo Cromossomo
		  Cromossomo c = new Cromossomo(this.length);

		  //Copia todos os gens
		  for(int i=0; i < this.length(); i++){
			 c._gens[i] = this._gens[i].clone();
		 }
		 //Avalia a qualidade do cromossomo
		 c.eval();
		 
		 //Retorna o cromossomo
		 return c;
	 }
	 
	 
	 /**
	  * Retorna uma string com informações do cromossomo
	  * @return String
	  */
	 public String toString(){
		 StringBuffer buf = new StringBuffer();
		 buf.append("Jobs: " + this.length);
		 
		 for(int i=0; i < this.length; i++){
			 buf.append("\r\n" + _gens[i].toString());
		 }
		 
		 buf.append("\r\nValor: " + this._value);
		 
		 return buf.toString();
	 }

	
	 /**
	  * Implementa a classe Comparable para poder ordenar os cromossomos e selecionar os melhores
	  */
	@Override
	public int compareTo(Cromossomo o) {
		//Verifica se esse objeto é pior que o cromossomo 'o'
		if(this._value < o.getValue()){
			return -1;
		}
		
		//Verifica se esse cromossomo é melhor que o cromossomo 'o'
		if(this._value > o.getValue()){
			return 1;
		}
		
		//Os cromossomos tem mesma qualidade
		return 0;
	}
	
	 /**
	  * Faz o swap de dois genes
	  * @param a - Cromossomo
	  * @param i - Gene a ser trocado com "change"
	  * @param change
	  */
	    protected void swap(int i, int change) {
			Gene helper = this.getGen(i);
			this.setGen(i, this.getGen(change));
			this.setGen(change, helper);
			this.eval();
		}
	    
	    /**
	     * Faz a mutação do Cromossomo
	     * Troca aleatória de dois genes de lugar com probabilidade p
	     * @param int n - tamanho da populaçao para calcular a probabilidade deste cromossomo mutar: prob = 1/n
	     */
	    public Cromossomo mutacao(int n){
	        double mutacao = (double)1 / (double)n;        
	                if (Math.random() < mutacao){ // se número sorteado é menor do que probabilidade
	                	//Ponto de quebra do cross-over
	                    int gen1 = (int)((this.length - 1)*Math.random()); // sorteia o primeiro gene
	                    int gen2 = (int)((this.length - 1)*Math.random()); // sorteia o segundo gene
	                	swap(gen1, gen2);
	                	this.eval();
	                }
	                
	                return this;
	    }
	    /**
	     * Ve se este cromossomo já tem o Gene g
	     * @param Gene a ser procurado
	     * @return <b>boolean</b> <br><b>true</b>: se tem o gene <br><b>false</b>: caso contrario
	     */
	    public boolean hasGen(Gene g){
	    	//Procura o gene
	    	for(Gene l:_gens){
	    		if(l == null)
	    			return false;
		    		
	    		if(l.equals(g))
		    	 return true; //encontrou - retorna true
	    	}

	    	return false; //não encontrou - retorna falso
	    }
	    
	    /**
	     * Retorna true se for um cromossomo valido
	     * @return
	     */
	    public boolean isValid(){
	    	return _valid;
	    }
	    
	    /**
	     * seta se o cromossomo eh valido
	     * @param b
	     */
	    public void setValid(boolean b){
	    	this._valid = b;
	    }
	

}
