import java.util.*;
import java.text.*;
import java.io.*;
class luciaTSP {

	int weight[][];
	int n;
	int camino[];
	int cost;
	int value_x[];
	int value_y[];
	public luciaTSP()
		{
			Scanner s = new Scanner (System.in);
			int[] Array = readFiles("P4tsp10.txt");
			n = Array[0];
			weight = new int[n][n];
			value_x = new int[n];
			value_y = new int[n];
			camino = new int[n-1];
			int k = 0;
			for (int i=1; i<Array.length; i = i+2){
			value_x[k] = Array[i];
			k++;
		}
		int d = 0;
		for (int i=2; i<Array.length; i = i+2)
			{
				value_y[d] = Array[i];
				d++;
			}
		for (int i = 0; i < n; i ++){
			for (int j = 0; j < n; j++)
			{
				double x = Math.pow((value_x[j] - value_x[i]),2);int x_ = (int) x;
				double y = Math.pow((value_y[j] - value_y[i]),2);
				int y_ = (int) y;
				double r = Math.sqrt( x_ + y_ );
				int w = (int) r;
				weight[i][j] = w;
			}
		}
		long startTime = System.currentTimeMillis();
		evaluate();
		//Time calculation
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			//print time
			System.out.println(" ");
			System.out.println("Miliseconds: ");
			System.out.println(totalTime);
	}

	public static int[] readFiles(String file)
		{
			try{
			File f = new File(file);
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt())
				{
				ctr++;
				s.nextInt();
				}
			int[] Array = new int [ctr];
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
	public int getCost(int currentCity, int input[], int size)
		{
		if (size == 0)//si no tine un tamaño   
		return weight[currentCity][0];// regresamos el peso 
		int min=999999;//asignamos un infinito
		int minIndex = 0;//asignamos un un indice para el minimo
		int setToCost[] = new int[n-1];//cremos un arreglo para guardar los costos 
		for (int i = 0; i < size; i++)
		{
			int k = 0; //inicializamos un nuevo 
			for (int j = 0; j < size; j++)
			{
				if(input[i] != input[j])//si el dato es diferente al que estamos analizando 
					{
					setToCost[k++] = input[j];//lo agrramo al costo
					}
			}
			int tmp = getCost(input[i], setToCost, size-1);// creamos una varible temporal y volvemos a evaluar el costo 
			if((weight[currentCity][input[i]]+tmp)<min)//si el peso de la ciudad es menor al infinito 
			{
				min = weight[currentCity][input[i]]+tmp;//ahora el minimo es el peso de la ciudad 
				minIndex = input[i];//y el indice sera el valor del set que se analizo 
			}
		}
		return min;
		}
		
	public int getMin(int currentCity, int input[], int size)
	{
		if(size == 0)
		return weight[currentCity][0];
		int min = 999999;
		int minIndex = 0;
		int setToCost[] = new int [n-1];
		for (int i = 0; i < size; i++)
		{ //consider each node of input set
			int k = 0; //initializate new set
			for (int j = 0; j < size; j++)
			{
				if(input[i] != input[j])
				{
				setToCost[k++] = input[j];
				}
			}
			int tmp = getCost(input[i], setToCost, size-1);
			if((weight[currentCity][input[i]]+tmp) < min)
			{
				min = weight[currentCity][input[i]]+tmp;
				minIndex = input[i];
			}
		}
		return minIndex;
		}
	public void evaluate()
		{
			int trySet[] = new int [n-1];//creamos un arreglo en el cual guardaremos todas los conjuntos 
			for(int i = 1; i < n; i++)
			trySet[i-1] = i;//iremos evaluando conjunto por conjunto 
			cost = getCost(0, trySet, n-1);//con la funcion costo sacaremos el costo   
			caminoContructor();
		}
	public void caminoContructor()
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
	public void printResult()
		{
			System.out.println("TSP Fuerza Bruta");	
			for(int i = 0; i < n-1; i++)
			{
				System.out.print((camino[i] + 1)+"->");
			}
			System.out.println();
			System.out.println(cost);
		}
	}
class bruteForce_TSP
	{
	public static void main(String args[])
		{
		//Initiating the calcultaion of time
		long startTime = System.currentTimeMillis();
		luciaTSP obj = new luciaTSP();
		//Time calculation
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		//print time
		System.out.println(" ");
		System.out.println("Miliseconds: ");
		System.out.println(totalTime);
		}
	}