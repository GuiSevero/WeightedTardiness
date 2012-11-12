
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Guilherme
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
    	String entrada = new String("wt50.txt");
    	int i=5000;
    	int time = 1;
    	//testCromossomo(entrada);
    	//testPopulation(entrada);
    		//for(i=0; i < 5; i++)
        		testExecutar("wt100.txt", "WT100_Out_1min_" + i + ".txt",25, 1 ); // 1 minuto
    		for(i=0; i < 3; i++){
    			/*
    			testExecutar("wt100.txt", "WT100_1_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
    			testExecutar("wt100-2.txt", "WT100_2_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
    			testExecutar("wt100-3.txt", "WT100_3_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-5.txt", "WT100_5_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-7.txt", "WT100_7_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-11.txt", "WT100_11_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-13.txt", "WT100_13_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-17.txt", "WT100_17_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-19.txt", "WT100_19_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   testExecutar("wt100-23.txt", "WT100_23_Out_1min" + i + ".txt",50, 1 ); // 1 minuto
        	   */
    		}
    		testCromossomo("wt100.txt");
    		testCromossomo("wt100-2.txt");
    		testCromossomo("wt100-3.txt");
    	   testCromossomo("wt100-5.txt");
     	   testCromossomo("wt100-7.txt");
     	   testCromossomo("wt100-11.txt");
     	   testCromossomo("wt100-13.txt");
     	   testCromossomo("wt100-17.txt");
     	   testCromossomo("wt100-19.txt");
     	   testCromossomo("wt100-23.txt");
    	
    	
    }
    
    public static void testPopulation(String doc){
    	AlgoritmoGenetico AG = new AlgoritmoGenetico(25, 1, doc);
    	AG.generatePopulation();
    	int menorVal = AG.getCromossomo(0).getValue();
    	int position = 0;
    	
    	//Cross-over
    	
    	AG.sort();
    	
    	for(int i=0; i < AG.populationSize(); i++){
    		//System.out.println("\n\n" + AG.getCromossomo(i).toString());
    		
    		if(AG.getCromossomo(i).getValue() < menorVal){
    			menorVal = AG.getCromossomo(i).getValue();
    			position = i;
    		}
    	}
    	
    	for(int i=0; i < 10; i++){
    		System.out.println("Valor Encontrado no cromossomo " + i + " : " + AG.getCromossomo(i).getValue());
    	}
    	
    	for(int i=0; i < 2; i++){
    		//System.out.println("Valor Encontrado no filho cromossomo " + i + " : " + filhos[i].getValue() + "\n\n");
    	}
    	
    	System.out.println("Menor Valor Encontrado no cromossomo " + position + " : " + menorVal + "\n\n");
    	//System.out.println("Cromossomo " + position + " : " + AG.getCromossomo(position).toString() );
    	
    }
    
    public static void testCromossomo(String doc){
    	
    	Cromossomo c = new Cromossomo(doc);
    	c.eval();
    	System.out.println("Cromossomo " + doc +": " + c.getValue());
    	
    }
    
    /**
     * 
     * @param entrada - nome do arquivo de entrada
     * @param saida - nome do arquivo de saída
     * @param pop - tamanho da população inicial
     * @param tempo - tempo maximo
     */
    public static void testExecutar(String entrada, String saida, int pop, int tempo){
    	AlgoritmoGenetico GA = new AlgoritmoGenetico(pop, tempo, entrada);
    	GA.generatePopulation();
    	try {
			GA.Executar(saida);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    	
}

