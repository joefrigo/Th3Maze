package maze;

import java.util.Random;

public class Utils {
	private Random rn = new Random();
	
	/**
	 * Will generate a number between a minimum value and a maximum value
	 * @param min minimum value of the generated value
	 * @param max maximum value of the generated value
	 * @return a number between min and max
	 */
	public int generateRandomInt(int min, int max){
		return rn.nextInt(max - min + 1) + min;
	}
	
	/**
	 * Will generate a number between 0 and the maximum value
	 * @param max the maximum value that can be generated
	 * @return return a number between 0 and max
	 */
	public int generateRandomInt(int max){
		return rn.nextInt(max);
	}
}
