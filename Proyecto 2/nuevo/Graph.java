import java.util.*;
import java.lang.*;
import java.io.*;

class Graph {
	Graph(){}
	static class Edge implements Comparable<Edge>//creamos la clase edge para los nodos 
	{
		int inicio, fin ;
		Float peso;//contiene inicio ,fin y el peso 
		public int compareTo(Edge compareEdge)  
		{							//creamos la funcion para relizar el sort de los nodos 
			if (peso<compareEdge.peso)return -1;
			else if (peso>compareEdge.peso)return +1;
			else return 0;
		}
	};
	
 
	static int V, E;  //los artistas y nodos
	static Edge edge[]; //cremos una arreglo de nodos
	
	Graph(int v, int e)	
	{					//constructor 
		V = v;
		E = e;
		
		edge = new Edge[E];//cremos los nodos con sus vetices 
		for (int i=0; i<e; ++i)
			edge[i] = new Edge();
	}
	static class subset//cremos una clase con el rango y el padre 
		{
			int parent, rank;
		};
	static int find(subset subsets[], int i)
	{					//cremos las funcion find
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);
 
		return subsets[i].parent;
	}
	static void Union(subset subsets[], int x, int y)
	{							//cremos las funcion union
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);
		if (xroot == yroot)
			return;
		else if (subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank)
			subsets[yroot].parent = xroot;
		else
		{
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	} 
	
	static Float KruskalMST()
	{
		long ti = System.currentTimeMillis();
		Edge result[] = new Edge[E];  //aqui guardaremos los nodos que formen el mst
		int e = 0; 
		int i = 0;  
		for (i=0; i<E; ++i)//creamos el espacio necesario para guardar los nodos 
			result[i] = new Edge();
		Arrays.sort(edge);//acomodamos los elementos de menor a mayor
 
		subset subsets[] = new subset[E];//creamos el espacio necesario para guardar los nodos
		for(i=0; i<E; ++i)
			subsets[i]=new subset();
 
		for (int v = 0; v < V; ++v)//cremos los padres y rangos 
		{
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}
		i = 0;   //indice del siguiente nodo
		while (e < V-1)//mientras no llegemos al final del los nodos 
		{
			//añadimos el mas pequeño 
			Edge next_edge = new Edge();
			next_edge = edge[i++];
 			
			int x = find(subsets, next_edge.inicio);
			int y = find(subsets, next_edge.fin);
 		
			if (x != y)//si no ahi ciclo agregmos el nodo sino lo desechamos
			{
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
			
		}
		System.out.println("Nodos del MST Kruskal");
		float total =0;
		for (i = 0; i < e; ++i){//imprimos los nodos que dan el mst 
		total=total+result[i].peso;
			System.out.println(result[i].inicio+" -- " + 
				   result[i].fin+" == " + result[i].peso);
		}
	System.out.println("Kruskal UF: "+total);
	ti -= System.currentTimeMillis();
	System.out.println("Se ejecuto en: "+ (ti*-1) + " mseg.");
		return total;
	}
	
	static Float kruskalUF(String inputFileName)throws FileNotFoundException 
	{
		Scanner in = new Scanner(new File(inputFileName));
		V = in.nextInt();//agremamos la primera letra 
		E = in.nextInt();//agremamos la segunda linea 
		Graph graph = new Graph(V, E);
		for (int i = 0; i < E; i++) //llenamos el grafo 
		{
			graph.edge[i].inicio = in.nextInt();//agregamos el incio de los nodos 
			graph.edge[i].fin = in.nextInt();//agregamos el fin de los nodos 	
			graph.edge[i].peso = in.nextFloat();//agregamos el peso 
		}
		in.close();//cerramos el archivo 
		return graph.KruskalMST();//regresamos el costo 
	}
	
	public static List<Nodo> e = new ArrayList<Nodo>();
	public static int maxArista;
	public static int maxNodos = 0;

	public static float prim(String inputFileName) throws FileNotFoundException  
	{
		Scanner sc = new Scanner(new File(inputFileName));
		maxNodos = sc.nextInt();
		maxArista = sc.nextInt();
		for (int i = 0; i < maxArista; i++)
			e.add(new Nodo(sc.nextInt(), sc.nextInt(), sc.nextInt()));

		sc.close();
		long ti = System.currentTimeMillis();
		Prim p = new Prim(e, maxNodos);
		System.out.println("Prim with heap: " + p.PrimHeap());
		ti -= System.currentTimeMillis();
		System.out.println("Se ejecuto en: "+ (ti*-1) + " mseg.");
		return p.PrimHeap();
	}

	
	static class Heap
	{
		private List<Nodo> lista;
		private int N=0, max=0;

		Heap(List<Nodo> l){//creamos el heap
		    lista = l;
			N = lista.size() - 1;
		}
		public void heap_sort(){//ordenamos el heap
			hep();

			for(int i = N; i > 0; i--){
				swap(lista, 0, i);
				N--;
				maxHeap(0);
			}
		}

	  private void hep(){
			for(int i = (N/2); i >= 0; i--)
				maxHeap(i);
		}

		private void maxHeap(int i){//obtenemos el maximo del heap
			int izq = i*2, der = i*2+1;
			if (izq <= N && lista.get(izq).greaterThan(lista.get(i))) max = izq;

			else max = i;

			if (der <= N && lista.get(der).greaterThan(lista.get(max))) max = der;

			if (max != i){
				swap(lista, i, max);
				maxHeap(max);
			}
		}

		private void swap(List<Nodo> arr,int x, int y){//intercambiamos los valores con un swap
			Nodo tmp = arr.get(x);
			arr.set(x, arr.get(y));
			arr.set(y, tmp);
		}
	};
	static  class Nodo {//cremos los nodos para el algoritmos de prim

		public int _origen;//inicio del nodo
		public int _destino;//fin del nodo
		public int _w;//peso  del nodo
		
		Nodo(int inicio, int fin, int peso ) 
		{//realizamos el contructor
			_origen = inicio;
			_destino = fin;
			_w = peso;
		}

	  public boolean greaterThan(Nodo other){//usamos la comparacion
	      return (_w > other._w);
	  }
	};
	static  class Prim 
	{
		public int costoTotal = 0, maxNodos;
		public List<Nodo> e = new ArrayList<Nodo>();
		private List<Integer> connected = new ArrayList<Integer>();


		Prim(List<Nodo> _e, int _maxNodos){
			e = _e;
			
			maxNodos = _maxNodos;
		}

		public float PrimHeap()
		{
			Heap h = new Heap(e);//creamos un heap 
			h.heap_sort();
			int i = 0;
			Nodo x;//creamos los nodos 

			connected.add(1);

			while(connected.size() < maxNodos) {//mientras no esten conectados
				x = e.get(i);
				if (((connected.contains(x._destino) && !connected.contains(x._origen))//si lo nodos no son iguales 
									|| (connected.contains(x._origen) && !connected.contains(x._destino)))) 
									{
							if (connected.contains(x._destino))
							{
								connected.add(x._origen);//añadimos el origen 
								System.out.println(x._origen +"--"+ x._destino+"=="+ x._w);
							}
							else
								connected.add(x._destino);//añadimos el destino  
							costoTotal += x._w;
							e.remove(x);//sino removemo el nodo 
							i = 0;
							}
				else
					i++;
			
			}
			
		return costoTotal;//regresamos el costo 
		}
	};
	
	
}

