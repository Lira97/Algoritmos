import java.util.*;
import java.lang.*;
import java.io.*;
public class TravelingSalesman 
{
	static int weight[][];//creamos una matriz para guardar los pesos
	static int n;
	static int camino[];//cremos un arreglo para guardar los nodos que represtaran el camino
	static int cost;//represetara el costo
	static int valueX[];//representa la coordenada X
	static int valueY[];//representa la coordenada y
	
	public static void TSPbruteForce(String file)
		{
			long startTime = System.currentTimeMillis();
			Scanner s = new Scanner (System.in);//leemos el archivo
			int[] Array = readFiles(file);
			n = Array[0];
			weight = new int[n][n];//le damos el tañano a los arreglos y la matriz 
			valueX = new int[n];
			valueY = new int[n];
			camino = new int[n-1];
			int k = 0;
			for (int i=1; i<Array.length; i = i+2){
			valueX[k] = Array[i];//vamos guardando los datos de x 
			k++;
		}
		int d = 0;
		for (int i=2; i<Array.length; i = i+2)
			{
				valueY[d] = Array[i];//vamos guardando los datos de Y
				d++;
			}
		for (int i = 0; i < n; i ++){
			for (int j = 0; j < n; j++)
			{
				double x = Math.pow((valueX[j] - valueX[i]),2);int x_ = (int) x;//sacamos la distancai entre un coordenada a tra 
				double y = Math.pow((valueY[j] - valueY[i]),2);
				int y_ = (int) y;
				double r = Math.sqrt( x_ + y_ );
				int w = (int) r;
				weight[i][j] = w;
			}
		}

		bruteForce();//iniciamos  el algoritmo de furza bruta 
		//Time calculation
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Miliseconds: ");
			System.out.println(totalTime);
	}

	public static int[] readFiles(String file)
		{
			try{
			File f = new File(file);//abrimos el archivo 
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt())//analizamos cada espacio 
				{
				ctr++;
				s.nextInt();//vamos 
				}
			int[] Array = new int [ctr];//agregamos los valores x y y al arreglo 
			Scanner s1 = new Scanner(f);
			for (int i = 0; i < Array.length; i++)
				Array[i] = s1.nextInt();
			return Array;
			}
		catch(Exception e)
			{
			return null;
			}
		}
	public static int getCost(int currentCity, int input[], int size)
		{
		if (size == 0)//si no tine un tamaño   
		return weight[currentCity][0];// regresamos el peso 
		int min=999999;//asignamos un minimo
		int minIndex = 0;//asignamos un un indice para el minimo
		int setToCost[] = new int[n-1];//cremos un arreglo para guardar los costos 
		for (int i = 0; i < size; i++)
		{
			int k = 0; //inicializamos un nuevo 
			for (int j = 0; j < size; j++)
			{
				if(input[i] != input[j])//si el dato es diferente al que estamos analizando 
					{
					setToCost[k++] = input[j];//lo agregamos al costo
					}
			}
			int tmp = getCost(input[i], setToCost, size-1);// creamos una varible temporal y volvemos a evaluar el costo 
			if((weight[currentCity][input[i]]+tmp)<min)//si el peso de la ciudad es menor al costo minom
			{
				min = weight[currentCity][input[i]]+tmp;//ahora el minimo es el peso de la ciudad 
				minIndex = input[i];//y el indice sera el valor del set que se analizo 
			}
		}
		return min;//regresamo el costo minimo
		}
	
	public static int getMin(int currentCity, int input[], int size)
	{
		if(size == 0)//si no tine un tamaño
		return weight[currentCity][0];// regresamos el peso 
		int min = 999999;//asignamos un infinito
		int minIndex = 0;
		int setToCost[] = new int [n-1];//cremos un arreglo para guardar los costos
		for (int i = 0; i < size; i++)
		{ 
			int k = 0; 
			for (int j = 0; j < size; j++)
			{
				if(input[i] != input[j])//si el dato es diferente al que estamos analizando 
				{
				setToCost[k++] = input[j];//lo agrramo al costo
				}
			}
			int tmp = getCost(input[i], setToCost, size-1);// creamos una varible temporal y volvemos a evaluar el costo
			if((weight[currentCity][input[i]]+tmp) < min)//si el peso de la ciudad es menor al infinito 
			{
				min = weight[currentCity][input[i]]+tmp;//ahora el minimo es el peso de la ciudad 
				minIndex = input[i];//y el indice sera el valor del set que se analizo
			}
		}
		return minIndex;//regresamo el camino 
		}
	public static void bruteForce()
		{
			int trySet[] = new int [n-1];//creamos un arreglo en el cual guardaremos todas los conjuntos 
			for(int i = 1; i < n; i++)
			trySet[i-1] = i;//iremos evaluando conjunto por conjunto 
			cost = getCost(0, trySet, n-1);//con la funcion costo sacaremos el costo   
			crearCamino();
		}
	public static void crearCamino()
		{
		int lastSet[]=new int[n-1];//creamos un arreglo para el ultimo conjunto  
		int nextSet[]=new int[n-2];//creamos un arreglo para el siguiente conjunto 
		
		for(int i=1;i<n;i++)
		lastSet[i-1]=i;//lo vamos llenado 
		
		int size=n-1;
		camino[0]=getMin(0,lastSet,size);//vamos fomrando el camino para llegar al menor costo 
		
		for(int i=1;i<n-1;i++)
		{
			int k=0;
			for(int j=0;j< size;j++)
			{
				if(camino[i-1]!=lastSet[j])//si el camino es diferente al ultimo conjunto 
				nextSet[k++]=lastSet[j];//entonces lo agregamos al conjunto  
			}
			--size;//restamos el tamaño
			camino[i]=getMin(camino[i-1],nextSet,size);//si no anlaizamos el siguiente ciudad 
			for(int j=0;j<size;j++)
			lastSet[j]=nextSet[j];//llenamos el siguiente arreglo 
		}
		printResult();//imprimos el camino  
		}
	public static void printResult()
		{
			System.out.println("TSP Fuerza Bruta");	
			for(int i = 0; i < n-1; i++)
			{
				System.out.print((camino[i] + 1)+"->");
			}
			System.out.println();
			System.out.println(cost);
		}
		
//----------------------------------TSP Dinamico ----------------------------------------------------------------		
	private static int INFINITY = 100000000;
	public static void TSPdinamico(String file)throws FileNotFoundException 
			{
				int V, E;
				double x1,y1,x2,y2,x,y;
				Scanner in = new Scanner(new File(file));
				V = in.nextInt();//agremamos la primera letra 
				double[ ][ ] aryNumbers = new double[V][2];
				double[ ][ ] costos = new double[V][V];
				for (int i = 0; i < V; i++) //llenamos el grafo 
				{
					for (int j = 0; j < 2; j++) //llenamos el grafo 
					{
						aryNumbers[i][j] = in.nextDouble();
						
					}
				}
				for (int i = 0; i < V; i++) //llenamos el grafo 
				{
					x1=aryNumbers[i][0];
					y1=aryNumbers[i][1];
					for (int j = 0; j < V; j++) //llenamos el grafo 
					{
						x2=aryNumbers[j][0];
						y2=aryNumbers[j][1];
						x=	x2-x1;	
						y=	y2-y1;	
						costos[i][j]=Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));		
							
					}
				}				
				in.close();//cerramos el archivo 
				
				TravelingSalesman graph = new TravelingSalesman();
				long startTime = System.currentTimeMillis();
				System.out.println(graph.minCost(costos));
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Miliseconds: ");
				System.out.println(totalTime);
				System.out.println();
				
			
			}
	private static class Index //creamos la clase index
	{
		int currentVertex;//creamos el nodo inicial
		Set<Integer> vertexSet;//cremos un conjunto de nodos

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Index index = (Index) o;

			if (currentVertex != index.currentVertex) return false;
			return !(vertexSet != null ? !vertexSet.equals(index.vertexSet) : index.vertexSet != null);
		}

		@Override
		public int hashCode()
		{
			int result = currentVertex;
			result = 31 * result + (vertexSet != null ? vertexSet.hashCode() : 0);
			return result;
		}

		private static Index createIndex(int vertex, Set<Integer> vertexSet) //funcion para crear el idice
		{
			Index i = new Index();//cremos un objeto indice
			i.currentVertex = vertex;//el nodo de currentVertex sera el nodo que le pasamos a la funcion
			i.vertexSet = vertexSet;// veterxSet sera el nuevo conjunto de elemntos que el pasamos
			return i;
		}
	}

	private static class SetSizeComparator implements Comparator<Set<Integer>>//funcion para comparar los tamanos de los conjuntos
	{
		@Override
		public int compare(Set<Integer> o1, Set<Integer> o2) {
			return o1.size() - o2.size();
		}
	}

	public Double minCost(double[][] distance)
	{

		Map<Index, Double> minCostDP = new HashMap<>();//creamos un mapa para guradar los costo minimos que vayamos analizando
		Map<Index, Integer> parent = new HashMap<>();//creamos un mapa para guradar los padres que vayamos analizando

		List<Set<Integer>> allSets = generateCombination(distance.length - 1);//generaremos todos las combinaciones y las gurdaremos en una lista

		for(Set<Integer> set : allSets) {//despues vamos a iterar por cada conjunto generado
			for(int currentVertex = 1; currentVertex < distance.length; currentVertex++)
			{
				if(set.contains(currentVertex)) {//si nodo ya fue evaluado entonces lo ignorara
					continue;
				}
				Index index = Index.createIndex(currentVertex, set);
				double minCost = INFINITY;
				int minPrevVertex = 0;
				//to avoid ConcurrentModificationException copy set into another set while iterating
				Set<Integer> copySet = new HashSet<>(set);
				for(int prevVertex : set) {//por cada nodo anteriro creado
					double cost = distance[prevVertex][currentVertex] + getCost(copySet, prevVertex, minCostDP);//el costo sera la dsitacioa masl el cosnto genrado
					if(cost < minCost) {//si el costo el menor al costo minmo
						minCost = cost;//costo minimo lo igualamos al costo
						minPrevVertex = prevVertex;//el igualamos el el nodo anterior
					}
				}

				if(set.size() == 0) {//si el subset esta vacio entoces le damos el valor de la distcion acutal en 0
					minCost = distance[0][currentVertex];//agregamos el csoto del valor en cero
				}
				minCostDP.put(index, minCost);//agregamos el costo minmo
				parent.put(index, minPrevVertex);//agregamos el padre
			}
		}

		Set<Integer> set = new HashSet<>();
		for(int i=1; i < distance.length; i++) {
			set.add(i);//vamos anadiendo
		}
		double min = Double.MAX_VALUE;//de damos el valor del infinito
		int prevVertex = -1;
		Set<Integer> copySet = new HashSet<>(set);//copiamos el hsah generado
		for(int k : set) {
			double cost = distance[k][0] + getCost(copySet, k, minCostDP);//agregamos el costo
			if(cost < min) {//si el csoto el menor al minmo
				min = cost;//agregamos el costo minmo
				prevVertex = k;//agregamos el padre
			}
		}

		parent.put(Index.createIndex(0, set), prevVertex);//agregamos el padre
		printTour(parent, distance.length);//impirmo el camino
		return min;//regresmo el csoto minmo
	}

	private void printTour(Map<Index, Integer> parent, int totalVertices)//imprimimos el camino
	{
		Set<Integer> set = new HashSet<>();
		for(int i=0; i < totalVertices; i++) //pasamos por todos los veritices
		{
			set.add(i);//anadimos los nodos
		}
		Integer start = 0;
		Deque<Integer> stack = new LinkedList<>();
		while(true)
		{
			stack.push(start);//agremos al srtack el valor del nodo inicio
			set.remove(start);//removemos el nodo incion
			start = parent.get(Index.createIndex(start, set));//obtemos el padre
			if(start == null)
			{
				break;
			}
		}
		StringJoiner joiner = new StringJoiner("->");
		stack.forEach(v -> joiner.add(String.valueOf(v)));//for cada volor en el stack
		System.out.println("\nTSP Dinamico");
		System.out.println(joiner.toString());//imprimos todos los valores del estack
	}

	private double getCost(Set<Integer> set, int prevVertex, Map<Index, Double> minCostDP) {
		set.remove(prevVertex);//quitamos el valor anterior
		Index index = Index.createIndex(prevVertex, set);//cresmo unnuevo indice para el valor anterior
		double cost = minCostDP.get(index);//regresmo el costo del arreglo indice
		set.add(prevVertex);//anadimos el nodo
		return cost;//regresmo el costo generados
	}

	private List<Set<Integer>> generateCombination(int n)
	{
		int input[] = new int[n];
		for(int i = 0; i < input.length; i++) {
			input[i] = i+1;//itermos todos los elemntos menos el primero
		}
		List<Set<Integer>> allSets = new ArrayList<>();//cremos una lista donde estatn todos los conjuntos
		int result[] = new int[input.length]; //le damos el mimo tamano al arreglo result
		generateCombination(input, 0, 0, allSets, result);//por medio de la funcoi crearcombinacion agramos los conjuntos
		Collections.sort(allSets, new SetSizeComparator());//los comparamos y los ordenamos
		return allSets;
	}

	private void generateCombination(int input[], int start, int pos, List<Set<Integer>> allSets, int result[]) {//funcion para generar todoas las combinaciones de los conjuntos
		if(pos == input.length) {//si se llega al inicio entonces nos salimos de la funcion
			return;
		}
		Set<Integer> set = createSet(result, pos);//cremos el conjunto para eso entramos al funcion crear conjunto
		allSets.add(set);//agremaos el conjubto creado
		for(int i=start; i < input.length; i++) {//iteramos del inicio hata el tamano del arreglo input
			result[pos] = input[i];//
			generateCombination(input, i+1, pos+1, allSets, result);//volvemoa entrar para seguir genrando los conjuntos
		}
	}

	private static Set<Integer> createSet(int input[], int pos) {
		if(pos == 0) {
			return new HashSet<>();//si la posicion es el inicio entoces regresmao el hash
		}
		Set<Integer> set = new HashSet<>();//cremos un hash
		for(int i = 0; i < pos; i++) {
			set.add(input[i]);//vamos anadiendo los conjuntos
		}
		return set;//regresamos los conjuntos
	}
		
	public static void main(String[] args) throws Exception 
	{
				TravelingSalesman ciudades = new TravelingSalesman(); 
				ciudades.TSPdinamico("P4tsp10.txt");
				ciudades.TSPbruteForce("P4tsp10.txt");
	}
}

