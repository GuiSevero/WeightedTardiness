
public class Gene implements Cloneable{
	 	public int weight = 0; //Peso desta tarefa
	    public int dueDate = 0; //Prazo esperado para esta tarefa
	    public int time = 0; //Tempo  que esta tarefa demora a ser executada
	    public int jobTime = 0; //Tempo que esta tarefa levou para ser executada
	    public int jobLateness = 0; //Medida de atraso que esta tarefa levou
	    
	    public String toString(){
	    	return
	    	 "t: " + time
	    	+ " | c: " + jobTime
	    	+ " | d: " + dueDate
	    	+ " | w: " + weight
	    	+ " | l: " + jobLateness;
	    	
	    }
	    
	    public Gene clone(){
	    	Gene g = new Gene();
	    	g.dueDate = this.dueDate;
	    	g.jobLateness = this.jobLateness;
	    	g.jobTime = this.jobTime;
	    	g.time = this.time;
	    	g.weight = this.weight;
	    	return g;
	    }
	    
	    /**
	     * verifica se o Gene g é igual a este
	     * @param g
	     * @return
	     */
	    public boolean equals(Gene g){
	    	if(
	    		this.time == g.time &&
	    		this.dueDate == g.dueDate &&
	    		this.weight == g.weight
	    		) return true;
	    	
			return false;
	    	
	    }
	    
}
