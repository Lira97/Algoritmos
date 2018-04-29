import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;
class Graph {
	
	static Vector<Edge> edges;
	static PriorityQueue<Integer> visited;
	static PriorityQueue<Edge> heap, next;
	static LinkedList<Integer>[] list;
	static float cost;
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
		else if (subsets[xroot].rank < subsets[yroot].rank)//si x es mayor ese sera el padre 
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank))//si y es mayor ese sera el padre 
			subsets[yroot].parent = xroot;
		else
		{
			subsets[yroot].parent = xroot;//regresmoa el valor de x como el padre 
			subsets[xroot].rank++;)//regresamos el valor de y como el rango
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
	System.out.println("Kruskal: "+total);
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

		public int origen;//inicio del nodo
		public int destino;//fin del nodo
		public int w;//peso  del nodo
		
		Nodo(int inicio, int fin, int peso ) 
		{//realizamos el contructor
			origen = inicio;
			destino = fin;
			w = peso;
		}

	  public boolean greaterThan(Nodo other){//usamos la comparacion
	      return (w > other.w);
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
				if (((connected.contains(x.destino) && !connected.contains(x.origen))//si lo nodos no son iguales 
									|| (connected.contains(x.origen) && !connected.contains(x.destino)))) 
									{
							if (connected.contains(x.destino))
							{
								connected.add(x.origen);//añadimos el origen 
								System.out.println(x.origen +"--"+ x.destino+"=="+ x.w);
							}
							else
								connected.add(x.destino);//añadimos el destino  
							costoTotal += x.w;
							e.remove(x);//sino removemo el nodo 
							i = 0;
							}
				else
					i++;
			
			}
			
		return costoTotal;//regresamos el costo 
		}
	};
	static Float kruskalDFS(String inputFileName)throws FileNotFoundException 
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
		
	static Float KruskalMst()
		{
			long ti = System.currentTimeMillis();
			 Edge e;
			list = new LinkedList[V]; // Lista de cada vértice del grafo
			for(int i = 0; i < list.length; i++){
				list[i] = new LinkedList<Integer>();
			}
			
			// Agregamos al heap las aristas (ordenadas por la PQ)
			addToHeap();
	 
			while (!heap.isEmpty())//mientras no llegemos al final del los nodos 
			{
				e = heap.remove();
				
				// Verificar si hay ciclos. En caso de que no, se añade la arista al MST
				if(DFS(list[e.inicio], e.fin, e.inicio,0) == 0){
					// Updatear listas
					list[e.inicio].add(e.fin);
					list[e.fin].add(e.inicio);
					cost += e.peso; // Aumentar costo agregando la arista
					// Impresión del nodo inicial, final y costo de la arista que los une
					System.out.println(e.inicio + "\t" + e.fin + "\t" + e.peso);
				}				
			}
			return cost;
	}
		
	static  int DFS(LinkedList<Integer> list, int searched_vertex, int curr_id, int prev_id)
		{
			int x = 0;
			
			
			if(list.isEmpty()){// Si la lista regresamos un0
				return 0;
			}
			
			
			if(list.contains(searched_vertex)){
				return 1;// Si encuentra le valor regresmos un 1
			}
			else
			{
				
				// Recorremos la lista de ayancencia
				for(int i = 0; i < list.size(); i++){
					// Si el id previo es diferente a los elementos de la lista
					if(prev_id != list.get(i)){
						// Volvemos a buscar un ciclo de forma recursiva dentro de la lista de ayancencia
						//x += DFS(list[list.get(i)],searched_vertex,list.get(i),curr_id);
					}
				}
			}
			
			// Devolvemos la respuesta
			return x;
		}
	static void addToHeap(){
		// Agrega TODAS las aristas al heap
		for(int i = 0; i < V-1; i++){
			heap.add(edge[i]);
		}
	}
}

