import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

class Arreglo 
{
	private int n;//varible para el tamaño del arreglo 
	private int[] arreglo;//varible para el arreglo 
	Arreglo()
	{
		n =0;
	}
	public void lecturaDatos(String archivo) throws Exception 
	{
		File file = new File(archivo);//cremos una variable file 
		BufferedReader br = new BufferedReader(new FileReader(file));//cremos un buffer para poder leer el archivo
		String st;//creamos una varible para guradar las lineas del archivo 
		int i=0;//varible para el contador 
		while ((st = br.readLine()) != null)//mientras la funcion readLine() no encuntre una linea sin caracteres
		{
			if(i==0)//if para leer la primera linea del archivo 
			{
				n=Integer.parseInt(st);//guardamos el primer valor del archivo en la varible n la cual es el tamaño del  arreglo y utilizamos a Integer.parseInt para pasar de un string a un int
				arreglo = new int[n];//creamos un arreglo con el tamaño definido anteriromente
			}
			else 
			{
				arreglo[i-1] = Integer.parseInt(st);//pasamos los valores de cada linea al arreglo 
			}
			i++;//sumamos uno al contador 
		}
	}
	class Graph
	{
		// A class to represent a graph edge
		class Edge implements Comparable<Edge>
		{
			int src, dest, weight;
	 
			// Comparator function used for sorting edges 
			// based on their weight
			public int compareTo(Edge compareEdge)
			{
				return this.weight-compareEdge.weight;
			}
		};
	 
		// A class to represent a subset for union-find
		class subset
		{
			int parent, rank;
		};
	 
		int V, E;    // V-> no. of vertices & E->no.of edges
		Edge edge[]; // collection of all edges
	 
		// Creates a graph with V vertices and E edges
		Graph(int v, int e)
		{
			V = v;
			E = e;
			edge = new Edge[E];
			for (int i=0; i<e; ++i)
				edge[i] = new Edge();
		}
	 
		// A utility function to find set of an element i
		// (uses path compression technique)
		int find(subset subsets[], int i)
		{
			// find root and make root as parent of i (path compression)
			if (subsets[i].parent != i)
				subsets[i].parent = find(subsets, subsets[i].parent);
	 
			return subsets[i].parent;
		}
	 
		// A function that does union of two sets of x and y
		// (uses union by rank)
		void Union(subset subsets[], int x, int y)
		{
			int xroot = find(subsets, x);
			int yroot = find(subsets, y);
	 
			// Attach smaller rank tree under root of high rank tree
			// (Union by Rank)
			if (subsets[xroot].rank < subsets[yroot].rank)
				subsets[xroot].parent = yroot;
			else if (subsets[xroot].rank > subsets[yroot].rank)
				subsets[yroot].parent = xroot;
	 
			// If ranks are same, then make one as root and increment
			// its rank by one
			else
			{
				subsets[yroot].parent = xroot;
				subsets[xroot].rank++;
			}
		}
	 
		// The main function to construct MST using Kruskal's algorithm
		void KruskalMST()
		{
			Edge result[] = new Edge[V];  // Tnis will store the resultant MST
			int e = 0;  // An index variable, used for result[]
			int i = 0;  // An index variable, used for sorted edges
			for (i=0; i<V; ++i)
				result[i] = new Edge();
	 
			// Step 1:  Sort all the edges in non-decreasing order of their
			// weight.  If we are not allowed to change the given graph, we
			// can create a copy of array of edges
			Arrays.sort(edge);
	 
			// Allocate memory for creating V ssubsets
			subset subsets[] = new subset[V];
			for(i=0; i<V; ++i)
				subsets[i]=new subset();
	 
			// Create V subsets with single elements
			for (int v = 0; v < V; ++v)
			{
				subsets[v].parent = v;
				subsets[v].rank = 0;
			}
	 
			i = 0;  // Index used to pick next edge
	 
			// Number of edges to be taken is equal to V-1
			while (e < V - 1)
			{
				// Step 2: Pick the smallest edge. And increment 
				// the index for next iteration
				Edge next_edge = new Edge();
				next_edge = edge[i++];
	 
				int x = find(subsets, next_edge.src);
				int y = find(subsets, next_edge.dest);
	 
				// If including this edge does't cause cycle,
				// include it in result and increment the index 
				// of result for next edge
				if (x != y)
				{
					result[e++] = next_edge;
					Union(subsets, x, y);
				}
				// Else discard the next_edge
			}
	 
			// print the contents of result[] to display
			// the built MST
			System.out.println("Following are the edges in " + 
										 "the constructed MST");
			for (i = 0; i < e; ++i)
				System.out.println(result[i].src+" -- " + 
					   result[i].dest+" == " + result[i].weight);
		}
	// Imprime el arreglo
	public  void imprimeArreglo()
	{
		for (int i=0; i<n; ++i)//ciclo que pasa por todos los elementos del arreglo
			System.out.print(arreglo[i]+" ");//imprime cada elemento 
		System.out.println();	//salto de linea 
	}
	
	// Ordena los elemento del arreglo de la clase usando el algoritmo Heap Sort. 
	public void heapSort()
	{
		// Construimos el heap 
		for (int i = n / 2 - 1; i >= 0; i--)
		{
			MAX_heapify(arreglo, n, i);
		}
 
		// Los movemos para poder reducirlo 
		for (int i=n-1; i>=0; i--)
		{
			// Movmeos la raiz hasta el ultimo elemento 
			int temp = arreglo[0];
			arreglo[0] = arreglo[i];
			arreglo[i] = temp;
 
			// se vulvea llamar la funcion para reducirlo 
			MAX_heapify(arreglo, i, 0);
		}

		
	}
	//funcion para poder crear el heap y oderarlo 
	public void MAX_heapify(int arr[], int n, int i) 
		{
			int raiz = i;  // Declaramos la varible raiz como el nodo raiz
			int izquierdo = 2*i + 1;  // hijo izquierdo 
			int derecho = 2*i + 2;  // hijo derecho  
	
			if (izquierdo < n && arr[izquierdo] > arr[raiz])//si el hijo izquierdo es mas grande que la raiz 
				raiz = izquierdo; //cambiamos el valor raiz por el del hijo izquierdo 
				
			if (derecho < n && arr[derecho] > arr[raiz])//si el hijo derecho es mas grande que la raiz 
				raiz = derecho;//cambiamos el valor raiz por el del hijo derecho 
	 
			if (raiz != i)//si el valor de la raiz es diferente al que teniamos al incio 
			{
				int temp = arr[i];//realizamos el cambio de varible y subimos el valor que es mas grande 
				arr[i] = arr[raiz];
				arr[raiz] = temp;
	
				MAX_heapify(arr, n, raiz);//hacemos una funcion recursiva para poder acomodar todos los elementos 
			}
		}
	// Ordena los elemento del arreglo de la clase usando el algoritmo Radix Sort. 
	public  void radixSort()
	{
		ArrayList<Integer> cubeta[] = new ArrayList[10]; // creamos un arrayList para guardar los elemntos 
		for (int count = 0; count < cubeta.length; count++) //vamos de 0 a 9 
		{
			cubeta[count] = new ArrayList<>(); //dentro de cada posicion volvemos a crear un arrayList 
		}

		boolean findMax = false;//variable para indicar el si se encontro elemento con mayor digitos
		int exp = 1;//varible para sacar cada digito
		while (!findMax) //mientra no sea false
		{
			findMax = true;
			for (Integer element : arreglo)//recorremos todo el arreglo
			 {
				cubeta[(element / exp) % 10].add(element);//agregamos los valores en la posicion del digito que se este analizando 
				if (findMax && ((element / exp) % 10)> 0) //mientras el elemento del arreglo no sea 0 no se va a cerrar el ciclo 
				{
					findMax = false;
				}
				
			}
			
			int a = 0;//variable para un contador 
			for (int b = 0; b < 10; b++) //recorremos las cubetas creadas 
			{
				for (Integer i : cubeta[b])//recorremos todos los elemento de la cubeta 
				{
					arreglo[a++] = i;//agregamos los valores al arregloa original 
					
				}
				cubeta[b].clear();//borramos la cubeta
			}
			
			exp = exp * 10;//multimplicamos el exp para que se pase al siguiente digito
		}
		
	 }
}
}
