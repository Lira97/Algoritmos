import java.util.Scanner;
class fibinacci 
{
	static int fibRec(int n)
	{
		if (n==0 || n==1)
			return n;
		else
			return fibRec(n-1)+fibRec(n-2);
	}
	
	static int fibIt(int n)
	{
		int [] array1 = new int[n+1];
		array1[0] =0;
		array1[1] =1;
		for (int i=2;i<=n;i++)
		{
			array1[i] =array1[i-1]+array1[i-2];
		}
		return array1[n];
	}
	
	public static void main(String[] args) 
	{
		int n = 0;
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		System.out.println("fib("+n+")="+ fibIt(n));
	}
}