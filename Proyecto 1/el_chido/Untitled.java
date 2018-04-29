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
	
	// Imprime el arreglo
	public  void imprimeArreglo()
	{
		for (int i=0; i<n; ++i)//ciclo que pasa por todos los elementos del arreglo
			System.out.print(arreglo[i]+" ");//imprime cada elemento 
		System.out.println();	//salto de linea 
	}
	
}