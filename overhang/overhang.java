package overhang;

import java.util.ArrayList;
import java.util.List;

public class overhang {

	/**
	 * @param args
	 */
	
	public static double f(int n){
		if (n == 0) {
			return 0.0;
		}else{
			double f = f(n-1)+(1.0/(n+1));
			return f;
			}
	}
	
	public static int min_n(float c){
		int n = 0;
		while (f(n) < c) {
			n++;
		}
		return n;
	}
	
	public static void main(String[] args) {
		// the input array can read from external file
		List input = new ArrayList(); 
		input.add(1.00);
		input.add(3.71);
		input.add(0.04);
		input.add(5.19);
		input.add(0.00);
		
		for (int i = 0; i<input.indexOf(0.00); i++) {
			System.out.println(min_n(Float.valueOf(input.get(i).toString())));
		}
	}
}
