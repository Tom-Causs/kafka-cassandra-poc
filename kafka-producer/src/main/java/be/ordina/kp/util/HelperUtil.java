package be.ordina.kp.util;

import java.util.List;
import java.util.Random;

public class HelperUtil {
	
	private static final Random RAND = new Random();

	public static String chooseRandomString (String[] stringArray) {
		int id = RAND.nextInt(stringArray.length);
		return stringArray[id];
	}
	
	public static int chooseIntBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
	
	public static boolean chooseRandomBoolean() {
		return RAND.nextBoolean();
	}
	
	public static Object chooseRandomListItem (List<?> list) {
		return list.get(RAND.nextInt(list.size()));
	}
	
	public static String chooseRandomIpAddress() {
		int a = RAND.nextInt(255);
        int b = RAND.nextInt(255);
        int c = RAND.nextInt(255);
        int d = RAND.nextInt(255);
        
        return a+"."+b+"."+c+"."+d;
	}
	
}
