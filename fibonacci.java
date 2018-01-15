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
	public static void main(String[] args) 
	{
		int n = 0;
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		System.out.println("fib("+n+")="+ fibRec(n));
	}
}