import java.util.Scanner;

public class MainClass
{
	public static void main(String args[])
	{
		
		//Task1____________________________________
		Scanner scr = new Scanner(System.in);
		int comparableArg = Integer.parseInt(args[0]);
		System.out.println("Hello world!");
		boolean flag = true;
		int len1 = args.length;
		//Here agrs are compare (first with others)
		for(int i = 0; i < len1; i++)
		{
			if(comparableArg!=Integer.parseInt(args[i]))
			flag = false;
		}
		//result of compare
		if(flag == true) System.out.println("Args are equal");
		else System.out.println("Args are different");
		
		//Task 2___________________________________________
		System.out.println("Task 2");
		
		//Initialization of array and indexes
		System.out.print("Enter startIndex:");
		int startIndex = scr.nextInt();
		System.out.print("Enter endIndex:");
		int endIndex = scr.nextInt();
		double[] array = new double[args.length];
		for(int i = 0; i < len1; i++)
		{
			array[i] = Double.parseDouble(args[i]);
		}
		
		//result of Task2
		System.out.println("Subarray: ");
		array = subarray(array, startIndex, endIndex);
		for(int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}

		//Task 3_____________________________________
		System.out.println("");
		System.out.println("Task 3 starts here");
		System.out.println("Enter the shift");
		int shift = scr.nextInt();
		String[] srt = new String[len1];
		System.out.print("The start string is: srt = ");
		for(int i = 0; i < len1; i++)
		{
			srt[i] = args[i];
			System.out.print(srt[i] + ", ");
		}
		System.out.println("");
		for(int i = 0; i < shift; i++)
		{
			shiftRight(srt, 1, len1);
		}
		System.out.println("The result string is: srt = ");
		for(int i = 0; i < len1;  i++){
		System.out.print(srt[i] + " ");
		}
	}
	//Task 2_________________________________________________________
	static double[] subarray(double[] array, int startIndex, int endIndex)
	{
		double[] resarray = new double[endIndex-startIndex];
		for(int i = startIndex; i<endIndex; i++)
		{
			resarray[i-startIndex] = array[i];
		}
		return resarray;
	}
	//Task 3__________________________________________________________________
	static String[] shiftRight(String[] srt, int shift, int len1)
	{
		String[] temp = new String[1];
		for (int i = 0; i < len1; i++ ) {
			temp[0] = srt[i];
			srt[i] = srt[len1 - 1];
			srt[len1 - 1] = temp[0];
		}
		return srt;
	}
}

	
