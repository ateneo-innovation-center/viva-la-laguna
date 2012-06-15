import java.io.*;
public class Rename{
	public static void main(String[] args){
		int x = args.length;
		System.out.println(x);
		for(int i = 0; i < x; i++){
			File a = new File(args[i]);
			String s = args[i].replace("-","");
			System.out.print(s+":::");
			File b = new File(s);
			System.out.println(a.renameTo(b));
		}
	}
}
