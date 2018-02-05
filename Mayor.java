public class Mayor
{
	public static void sort(int arr[],int n)
	{
		int mayor = arr[0];
		int segundo = arr[0];
		for (int i = 1 ; i < n; i++)
		{
			if(mayor<arr[i])
			{
				mayor =arr[i];
			}
			else if(segundo>arr[i])
				{
					segundo=arr[i];
					
				}	
			
		}
		System.out.print(mayor+"\n");
		System.out.print(segundo);

	}
 
		/* A utility function to print array of size n */
	static void printArray(int arr[])
	{
		int n = arr.length;
		for (int i=0; i<n; ++i)
			System.out.print(arr[i]+" ");
		System.out.println();
	}
 
	// Driver program
	public static void main(String args[])
	{
		int arr[] = {11, 86, 12,980, 126, 7};
		int n = arr.length;
		sort(arr,n);
	}
}