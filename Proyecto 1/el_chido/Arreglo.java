import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

class Arreglo {
	public static int n;//varible para el tamaño del arreglo 
	public static int[] arreglo;//varible para el arreglo 
	public static void lecturaDatos(String archivo) throws Exception 
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
	
	// Imprime el arreglo
	public static void imprimeArreglo()
	{
		for (int i=0; i<n; ++i)//ciclo que pasa por todos los elementos del arreglo
			System.out.print(arreglo[i]+" ");//imprime cada elemento 
		System.out.println();	//salto de linea 
	}
	
	// Ordena los elemento del arreglo de la clase usando el algoritmo Heap Sort. 
	public static void heapSort()
	{
		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
		{
			MAX_heapify(arreglo, n, i);
		}
 
		// One by one extract an element from heap
		for (int i=n-1; i>=0; i--)
		{
			// Move current root to end
			int temp = arreglo[0];
			arreglo[0] = arreglo[i];
			arreglo[i] = temp;
 
			// call max heapify on the reduced heap
			MAX_heapify(arreglo, i, 0);
		}

		
	}
	//funcion para poder crear el heap y oderarlo 
	public static void MAX_heapify(int arr[], int n, int i) 
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
	public static void radixSort()
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
	
	public static void main(String[] args) throws Exception 
	{	
		String archivo = "Test.txt";//varibel con el nombre del arreglo  
		lecturaDatos(archivo);//funcion para leer los numero del archivo
		imprimeArreglo();//funcion para imprimir arreglo 
		heapSort();//funcion para heapSort
		radixSort();//funcion para radixSort
		imprimeArreglo();//funcion para imprimir arreglo 
	}
}
