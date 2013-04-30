WeightedTardiness
=================

### UNIVERSIDADE FEDERAL DO RIO GRANDE DO SUL - Junho / 2012
---------------
INF05010 - Otimização Combinatória (Luciana Buriol).

Implementação de um Algoritmo Genético para resolução do problema Weighted Tardiness

### Sequenciamento em uma máquina minimizando o atraso 

- Autores: Guilherme Severo e Claudio Busatto

- Definição do problema: http://people.brunel.ac.uk/~mastjjb/jeb/orlib/wtinfo.html

- Apresentação - https://github.com/GuiSevero/WeightedTardiness/blob/master/Apresentacao.pdf

- JAVA 

### Manual: 
- Descompacte o projeto em uma pasta.
- Mude a classe Main.java para testar os arquivos de entrada da sua escolha
 

```java
public static void main(String[] args) throws Exception {
         	 		
	testExecutar("wt100.txt", "WT100_1_Out_1min" + i + ".txt",50, 1 ); // 1 minuto    	       	
	
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


```

  


