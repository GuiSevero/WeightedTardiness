import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Guilherme e Claudio
 * @
 */
public class WT {
	
	    private int _numItens; // Numero de Jobs
	    private int[] _weights; //Pesos de cada tarefa
	    private int[] _dueDates; //Prazos de cada tarefa
	    private int[] _times; //Tempo  que cada tarefa demora a ser executada
	    
	    private int[] _order; // Ordem em que as tarefas são executadas
	    private int[] _jobTimes; //Tempo em que cada job terminou
	    private int[] _jobLateness; //Lateness de cada job
	    
	    private Gene[] _cromossomo;
	    
	    private int _value = -1; //Valor de avaliação deste cromossomo
	    
	    public WT(String document){
	    	try {
				this.fetchDocument(document);
				//Seta o numero de itens
		    	this._order = new int[this._numItens];
		    	this._jobLateness = new int[this._numItens];
		    	this._jobTimes = new int[this._numItens];
		    	//Seta a ordem inicial  que será realizada as tarefas
		    	
		    	for(int i=0 ; i < _numItens; i++){
		    		_order[i] = i;
		    	}
		    	
		    	this.calcJobTimes();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new Exception("Arquivo Invalido!");
			}
	    }
	    
	    /**
	     * 
	     * @param times - Os tempos de cada tarefa
	     * @param weights - Os pesos de cada tarefa
	     * @param dueDates - Os prazos de cada tarefa
	     */
	    public WT(int[] times, int[] weights, int[] dueDates){
	        
	    	this._times = times;
	    	this._weights = weights;
	    	this._dueDates = dueDates;
	    	
	    	
	    	this._jobLateness = new int[_numItens];
	    	this._jobTimes = new int[_numItens];

	    	//Seta a ordem inicial que será realizada as tarefas
	    	for(int i=0 ; i < _numItens; i++){
	    		_order[i] = i;
	    	}
	    	
	    	this.calcJobTimes();
	    }
	    
	    
	    /**
	     * Calcula em que tempo cada tarefa termina
	     */
	    public void calcJobTimes(){
	    	//inicia valor de avalização
	    	_value = 0;
	    	
	    	for(int i=0 ; i < _numItens ; i++){
	    		int soma = 0;
	    		for(int j=0; j <= i; j++){
	    			soma += _times[_order[j]];
	    		}
	    		//Calcula o job time
	    		_jobTimes[i] = soma;
	    		//Calcula o lateness
	    		_jobLateness[i] = Math.max(_jobTimes[i] - _dueDates[i],0);
	    		
	    		_value += _jobLateness[i] * _weights[i];
	    	}
	    }
	    
	    public  void shuffleArray(int[] a) {
			int n = a.length;
			Random random = new Random();
			random.nextInt();
			for (int i = 0; i < n; i++) {
				int change = i + random.nextInt(n - i);
				swap(a, i, change);
			}
		}
	    
	    protected void swap(int[] a, int i, int change) {
			int helper = a[i];
			a[i] = a[change];
			a[change] = helper;
		}
	    
	    
	    /**
	     * 
	     * @param document - nome do arquivo
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
           	_numItens = pars.size() / 3;
           	
           _times = new int[_numItens];
           _weights = new int[_numItens];
           _dueDates = new int[_numItens];
           //Instancia todos os tempos
           for(int i=0;i < pars.size() / 3; i++){
        	   _times[i] = pars.get(i);
           }
           
           //Instancia todos os pesos
           for(int i = pars.size() / 3, j=0; i < 2 * pars.size() / 3; i++, j++){
        	   _weights[j] = pars.get(i);
           }
           
           //Instancia todos os due dates
           for(int i = 2 * pars.size() / 3, j=0;i < pars.size(); i++, j++){
        	  _dueDates[j] = pars.get(i);
           }
           
    }
	    
	    
	    
	    /*
	     * Getters and Setters
	     */
	    
	    public int[] getWeights() {
	        return _weights;
	    }
	    
	    public int getWeight(int i) {
	        return _weights[i];
	    }
	    
	    public int[] getDueDates() {
	        return _dueDates;
	    }
	    
	    public int getDueDate(int i) {
	        return _dueDates[i];
	    }
	    
	    public int[] getTimes() {
	        return _times;
	    }
	    
	    public int getTime(int i) {
	        return _times[i];
	    }

	    public int getNumItens(){
	    	return _numItens;
	    }
	    
	    public int getOrder(int n){
	    	return _order[n];
	    }
	    
	    public int[] getOrder(){
	    	return _order;
	    }
	    
	    public int[] getJobTimes(){
	    	return _jobTimes;
	    }
	    public int getJobTime(int i){
	    	return _jobTimes[i];
	    }
	    
	    public int getLateness(int i){
	    	return _jobLateness[i];
	    }
	    
	    public int[] getLatenesses(){
	    	return _jobLateness;
	    }
	    
	    public int getValue(){
	    	return _value;
	    }
	    
	    public void setNumItens(int n){
	    	_numItens = n;
	    }
	    
	    public void setOrder(int p, int o){
	    	_order[p] = o;
	    }
	    
	    public void setOrder(int order[]){
	    	_order = order;
	    }


}
