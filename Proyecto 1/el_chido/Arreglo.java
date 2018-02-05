// Java Program to illustrate reading from Text File
// using Scanner Class
import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
class Arreglo {
	public static int n;
	public static int[] arreglo;
	// Lee los datos de una archivo cuyo nombre recibe como parámetro 
	public static void lecturaDatos(String archivo)throws Exception 
	{
		File file = new File(archivo);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int i=0;
		while ((st = br.readLine()) != null)
		{
			if(i==0)
			{
				n=Integer.parseInt(st);
				arreglo = new int[n];
			}
			else 
			{
				arreglo[i-1] = Integer.parseInt(st);
			}
			i++;
		}
	}
	// Imprime el arreglo
	public static void imprimeArreglo()
	{
		for (int i=0; i<n; ++i)
			System.out.print(arreglo[i]+" ");
		System.out.println();	
	}
	// Ordena los elemento del arreglo de la clase usando el algoritmo Heap Sort. 
	public static void heapSort()
	{
		
	}
	// Ordena los elemento del arreglo de la clase usando el algoritmo Radix Sort. 
	public static void radixSort()
	{
		int RADIX = 10;

		ArrayList<Integer> bucketsArray[] = new ArrayList[RADIX];
		for (int count = 0; count < bucketsArray.length; count++) 
		{
			bucketsArray[count] = new ArrayList<>();
		}

		boolean maxDigitsLengthReached = false;
		int placeValue = 1;
		while (!maxDigitsLengthReached) 
		{
			maxDigitsLengthReached = true;
			for (Integer element : arreglo)
			 {
				bucketsArray[(element / placeValue) % RADIX].add(element);
				if (maxDigitsLengthReached && ((element / placeValue) % RADIX)> 0) 
				{
					maxDigitsLengthReached = false;
				}
			}
			
			int a = 0;
			for (int b = 0; b < RADIX; b++) 
			{
				for (Integer i : bucketsArray[b])
				{
					arreglo[a++] = i;
				}
				bucketsArray[b].clear();
			}
			placeValue = placeValue * RADIX;
		}
		
	}
	
	public static void main(String[] args) throws Exception 
	{	
		String archivo = "Test.txt";
		lecturaDatos(archivo);
		imprimeArreglo();
		heapSort();
		radixSort();
		imprimeArreglo();
	}
}
